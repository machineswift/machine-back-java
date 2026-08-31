package com.machine.starter.security.handler.oauth2;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.model.AppResult;
import com.machine.starter.security.util.MachineOAuth2ErrorUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 令牌端点（/token）认证失败响应处理器
 * <p>
 * 将 Spring Authorization Server 默认的 {@code {error, error_description}} 响应
 * 统一包装为项目标准的 {@link AppResult} 结构。
 */
@Slf4j
@Component
public class MachineOAuth2TokenEndpointErrorHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull AuthenticationException exception) throws IOException {
        String errorCode;
        String errorMessage;
        HttpStatus status;

        if (exception instanceof OAuth2AuthenticationException oauth2Exception) {
            OAuth2Error error = oauth2Exception.getError();
            errorCode = "iam.auth.identity.oauth2." + error.getErrorCode();
            errorMessage = MachineOAuth2ErrorUtil.resolveMessage(error);

            // RFC 6749 §5.2：invalid_client 返回 401，其余参数错误返回 400
            status = OAuth2ErrorCodes.INVALID_CLIENT.equals(error.getErrorCode())
                    ? HttpStatus.UNAUTHORIZED
                    : HttpStatus.BAD_REQUEST;
        } else {
            errorCode = "iam.auth.identity.oauth2.tokenEndpointFailed";
            errorMessage = "令牌获取失败";
            status = HttpStatus.BAD_REQUEST;
        }

        log.warn("令牌端点认证失败，code={}，message={}", errorCode, errorMessage);

        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        AppResult<String> result = AppResult.fail(errorCode, errorMessage);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
