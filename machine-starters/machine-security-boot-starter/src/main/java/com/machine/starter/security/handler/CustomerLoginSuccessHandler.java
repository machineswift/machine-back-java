package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.model.AppResult;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.security.login.IamAuthLoginResponse;
import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.LoginLogUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.security.SecurityConstant.*;

@Slf4j
@Component
public class CustomerLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MachineJwtUtil machineJwtUtils;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 生成JWT accessToken
        String accessTokenId = UUID.randomUUID().toString().replaceAll("-", "");
        long accessTokenExpire = System.currentTimeMillis() + AUTH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4AuthToken = new HashMap<>();
        claimMap4AuthToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, accessTokenId);
        claimMap4AuthToken.put(USER_ID_KEY, AppContext.getContext().getUserId());
        String accessToken = machineJwtUtils.generateToken(
                authentication.getName(),
                claimMap4AuthToken,
                accessTokenExpire);

        //生成JWT refreshToken
        String refreshTokenId = UUID.randomUUID().toString().replaceAll("-", "");
        long refreshTokenExpire = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4RefreshToken = new HashMap<>();
        claimMap4RefreshToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, refreshTokenId);
        claimMap4RefreshToken.put(USER_ID_KEY, AppContext.getContext().getUserId());
        claimMap4RefreshToken.put(AUTH_TOKEN_REFRESH_TOKEN_KEY, AUTH_TOKEN_REFRESH_TOKEN_KEY);
        String refreshToken = machineJwtUtils.generateToken(
                authentication.getName(),
                claimMap4RefreshToken,
                refreshTokenExpire);

        //新增登录成功日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(AppContext.getContext().getUserId()));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.LOGIN);
        inputDto.setAuthMethod(AppContext.getContext().getAuthMethod());
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setRefreshTokenId(refreshTokenId);
        inputDto.setAccessTokenExpire(accessTokenExpire);
        inputDto.setRefreshTokenExpire(refreshTokenExpire);
        inputDto.setAccessToken(accessToken);
        inputDto.setRefreshToken(refreshToken);
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);

        IamAuthLoginResponse loginResponse = new IamAuthLoginResponse(accessToken, accessTokenExpire, refreshToken);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        AppResult<IamAuthLoginResponse> result = AppResult.success(loginResponse);
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}