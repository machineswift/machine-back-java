package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.sdk.base.model.AppResult;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.MachineLoginLogUtil;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_TOKEN_ID;
import static com.machine.starter.security.config.SecurityConstant.*;
import static com.machine.starter.security.util.MachineLoginLogUtil.blackAllAvailableToken;

@Component
public class MachineLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserLoginLogClient loginLogClient;

    @Override
    public void onLogoutSuccess(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        /*
         * 注销登录时，缓存JWT至Redis，且缓存有效时间设置为JWT的有效期。
         * 请求资源时判断是否存在缓存的黑名单中，存在则拒绝访问。
         */
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        Jwt claimHeader = machineJwtUtil.getClaimsByToken(jwt.substring(BEARER_TYPE.length() + 1));
        String accessTokenId = claimHeader.getId();

        // 验证是否注销过
        if (null == customerRedisCommands.get(BIAM_AUTH_TOKEN_ID + claimHeader.getId())) {
            Duration ttl = Duration.between(Instant.now(), claimHeader.getExpiresAt());
            long ttlSeconds = Math.max(1, ttl.getSeconds());
            customerRedisCommands.setex(BIAM_AUTH_TOKEN_ID + accessTokenId,
                    claimHeader.getClaim(USER_ID_KEY).toString(), ttlSeconds);

            String currentUserId = claimHeader.getClaim(USER_ID_KEY).toString();
            AppContextHolder.getContext().setUserId(currentUserId);
            BIamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient
                    .getLoginSuccessByAccessTokenId(accessTokenId);

            List<String> hasProcessLoginLogList = blackAllAvailableToken(currentUserId, loginLogClient,
                    customerRedisCommands);

            // 新增注销日志
            BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(currentUserId));
            BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
            inputDto.setAuthAction(BIamAuthActionEnum.LOGOUT);
            inputDto.setAuthMethod(detailOutputDto.getAuthMethod());
            inputDto.setAuthResult(BIamAuthResultEnum.SUCCESS);
            inputDto.setAccessTokenId(accessTokenId);
            inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
            // 记录被联动处理的日志ID
            inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
            MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
            loginLogClient.create(inputDto);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        AppResult<String> result = AppResult.success("注销成功");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
