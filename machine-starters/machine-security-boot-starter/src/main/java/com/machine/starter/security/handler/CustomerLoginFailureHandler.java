package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.authentication.AuthFeignUserIdException;
import com.machine.sdk.common.exception.iam.authentication.AuthInterceptorUserIdException;
import com.machine.sdk.common.exception.iam.authentication.UserStatusDisableException;
import com.machine.sdk.common.model.AppResult;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.security.exception.CaptchaWrongAuthenticationException;
import com.machine.starter.security.exception.PhoneNotFoundException;
import com.machine.starter.security.util.LoginLogUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomerLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        AppResult<String> result;
        if (e instanceof CaptchaWrongAuthenticationException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.captchaWrong", e.getMessage());
        } else if (e instanceof AuthFeignUserIdException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.feignApplyUserIdLost", e.getMessage());
        } else if (e instanceof AuthInterceptorUserIdException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.interceptorPreHandleUserIdLost", e.getMessage());
        } else if (e instanceof UserStatusDisableException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.userStatusDisable", e.getMessage());
        } else if (e instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.usernameOrPasswordWrong", "用户名或密码不正确");
        } else if (e instanceof PhoneNotFoundException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication.phoneNotFound",
                    "您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            result = AppResult.fail("iam.auth.authentication", e.getMessage());
        }

        //新增登录失败日志
        if (null != AppContext.getContext().getUserId()) {
            IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(AppContext.getContext().getUserId()));
            IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
            inputDto.setAuthAction(IamAuthActionEnum.LOGIN);
            inputDto.setAuthMethod(AppContext.getContext().getAuthMethod());
            inputDto.setAuthResult(IamAuthResultEnum.FAIL);
            inputDto.setFailReason(result.getMessage());
            LoginLogUtil.setUserAgentInfo(request, inputDto);
            loginLogClient.create(inputDto);
        }

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}