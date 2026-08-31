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
import com.machine.starter.security.util.MachineLoginLogUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MachineLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserLoginLogClient loginLogClient;

    @Override
    public void onAuthenticationFailure(@NonNull HttpServletRequest request,
                                        HttpServletResponse response,
                                        @NonNull AuthenticationException authenticationException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");


        AppResult<String> appResult;
        if (authenticationException instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            appResult = AppResult.fail("iam.auth.authentication.usernameOrPasswordWrong", "用户名或密码不正确");
        } else if (authenticationException instanceof BadCredentialsException) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            appResult = AppResult.fail("iam.auth.authentication.badCredentials", authenticationException.getMessage());
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            appResult = AppResult.fail("iam.auth.authentication.exception", authenticationException.getMessage());
        }

        //新增登录失败日志
        if (null != AppContextHolder.getContext().getUserId()) {
            BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(AppContextHolder.getContext().getUserId()));
            BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
            inputDto.setAuthAction(BIamAuthActionEnum.LOGIN);
            inputDto.setAuthMethod(AppContextHolder.getContext().getAuthMethod());
            inputDto.setAuthResult(BIamAuthResultEnum.FAIL);
            inputDto.setFailReason(appResult.getMessage());
            MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
            loginLogClient.create(inputDto);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(appResult).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}