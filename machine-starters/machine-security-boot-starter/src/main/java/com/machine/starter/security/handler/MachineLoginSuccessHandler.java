package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.sdk.base.model.AppResult;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.starter.security.service.model.MachineAuthenticationResult;
import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.MachineLoginLogUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.security.config.SecurityConstant.*;

@Slf4j
@Component
public class MachineLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MachineJwtUtil machineJwtUtils;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserLoginLogClient loginLogClient;

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 生成JWT accessToken
        String accessTokenId = UUIDv7.generateWithoutDashes();
        long accessTokenExpire = System.currentTimeMillis() + AUTH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4AuthToken = new HashMap<>();
        claimMap4AuthToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, accessTokenId);
        claimMap4AuthToken.put(USER_ID_KEY, AppContextHolder.getContext().getUserId());
        String accessToken = machineJwtUtils.generateToken(
                authentication.getName(),
                claimMap4AuthToken,
                accessTokenExpire);

        //生成JWT refreshToken
        String refreshTokenId = UUIDv7.generateWithoutDashes();
        long refreshTokenExpire = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4RefreshToken = new HashMap<>();
        claimMap4RefreshToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, refreshTokenId);
        claimMap4RefreshToken.put(USER_ID_KEY, AppContextHolder.getContext().getUserId());
        claimMap4RefreshToken.put(AUTH_TOKEN_REFRESH_TOKEN_KEY, AUTH_TOKEN_REFRESH_TOKEN_KEY);
        String refreshToken = machineJwtUtils.generateToken(
                authentication.getName(),
                claimMap4RefreshToken,
                refreshTokenExpire);

        //新增登录成功日志
        BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(AppContextHolder.getContext().getUserId()));
        BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(BIamAuthActionEnum.LOGIN);
        inputDto.setAuthMethod(AppContextHolder.getContext().getAuthMethod());
        inputDto.setAuthResult(BIamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setRefreshTokenId(refreshTokenId);
        inputDto.setAccessTokenExpire(accessTokenExpire);
        inputDto.setRefreshTokenExpire(refreshTokenExpire);
        MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        AppResult<MachineAuthenticationResult> authenticationResult = AppResult.success(
                new MachineAuthenticationResult(accessToken, accessTokenExpire, refreshToken));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(authenticationResult).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}