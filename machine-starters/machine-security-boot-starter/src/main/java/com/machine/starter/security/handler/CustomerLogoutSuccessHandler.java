package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.authentication.JwtTokenBlackException;
import com.machine.sdk.common.model.AppResult;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.LoginLogUtil;
import io.jsonwebtoken.Claims;
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
import java.util.*;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_TOKEN_ID;
import static com.machine.starter.security.SecurityConstant.*;
import static com.machine.starter.security.util.LoginLogUtil.blackAllAvailableToken;

@Component
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

         /*
            注销登录时，缓存JWT至Redis，且缓存有效时间设置为JWT的有效期。
            请求资源时判断是否存在缓存的黑名单中，存在则拒绝访问。
         */
        String jwt = request.getHeader(AUTH_TOKEN_HEADER_KEY);
        Claims claimHeader = machineJwtUtil.getClaimsByToken(jwt.substring(BEARER_TYPE.length() + 1));
        String accessTokenId = claimHeader.getId();

        //验证是否为黑名单
        if (null != customerRedisCommands.get(IAM_AUTH_TOKEN_ID + claimHeader.getId())) {
            throw new JwtTokenBlackException("登录失效，请重新登录");
        }

        customerRedisCommands.set(IAM_AUTH_TOKEN_ID + accessTokenId,
                claimHeader.get(USER_ID_KEY).toString(),
                (claimHeader.getExpiration().getTime() - System.currentTimeMillis()) / 1000);

        String currentUserId = claimHeader.get(USER_ID_KEY).toString();
        AppContext.getContext().setUserId(currentUserId);
        IamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByAccessTokenId(accessTokenId);

        List<String> hasProcessLoginLogList = blackAllAvailableToken(machineJwtUtil, currentUserId, loginLogClient, customerRedisCommands);

        //新增注销日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(currentUserId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.LOGOUT);
        inputDto.setAuthMethod(detailOutputDto.getAuthMethod());
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
        inputDto.setAccessToken(detailOutputDto.getAccessToken());
        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        AppResult<String> result = AppResult.success("注销成功");
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
