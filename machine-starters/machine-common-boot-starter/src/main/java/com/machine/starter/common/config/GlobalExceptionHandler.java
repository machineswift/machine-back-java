package com.machine.starter.common.config;

import com.machine.sdk.common.exception.BusinessException;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceBlackException;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceClientException;
import com.machine.sdk.common.exception.iam.authentication.*;
import com.machine.sdk.common.model.AppResult;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenExpireException.class)
    public AppResult<Objects> errorHandler(AuthTokenExpireException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.authTokenExpire", exception.getMessage());
    }

    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenParsingException.class)
    public AppResult<Objects> errorHandler(AuthTokenParsingException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.authTokenParsingFailed", exception.getMessage());
    }


    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenUseException.class)
    public AppResult<Objects> errorHandler(AuthTokenUseException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.authTokenUseWrong", exception.getMessage());
    }

    /**
     * RefreshToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RefreshTokenUseException.class)
    public AppResult<Objects> errorHandler(RefreshTokenUseException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.refreshTokenUseWrong", exception.getMessage());
    }

    /**
     * JwtToken黑名单异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = JwtTokenBlackException.class)
    public AppResult<Objects> errorHandler(JwtTokenBlackException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.jwtTokenBlack", exception.getMessage());
    }

    /**
     * 用户禁用
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserStatusDisableException.class)
    public AppResult<Objects> errorHandler(UserStatusDisableException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication.userStatusDisable", exception.getMessage());
    }


    /**
     * 认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthenticationException.class)
    public AppResult<Objects> errorHandler(AuthenticationException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication", exception.getMessage());
    }

    /**
     * OpenApi黑名单异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = OpenApiResourceBlackException.class)
    public AppResult<Objects> errorHandler(OpenApiResourceBlackException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.accessDenied.openApiResourceBlack", exception.getMessage());
    }

    /**
     * OpenApi没有带clientId
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = OpenApiResourceClientException.class)
    public AppResult<Objects> errorHandler(OpenApiResourceClientException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.accessDenied.wrongClientId", exception.getMessage());
    }

    /**
     * 权限异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccessDeniedException.class)
    public AppResult<Objects> errorHandler(AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.accessDenied", "您没有权限执行此操作");
    }

    /**
     * 参数异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AppResult<Objects> errorHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        List<String> errorMessages = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        String combinedMessage = String.join(", ", errorMessages);

        return AppResult.fail("param.valid.exception", combinedMessage);
    }

    /**
     * 业务异常处理
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    public AppResult<Objects> errorHandler(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(exception.getCode(), exception.getMessage());
    }


    /**
     * 503异常处理
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = FeignException.ServiceUnavailable.class)
    public AppResult<Objects> errorHandler(FeignException.ServiceUnavailable exception) {
        log.error(exception.getMessage(), exception);

        String serviceName = extractServiceName(exception.getMessage());
        String errorMessage = String.format("服务[%s]不可用", serviceName);
        return AppResult.fail("SERVICE_UNAVAILABLE", errorMessage);
    }

    /**
     * 500异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public AppResult<Objects> errorHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("exception", exception.getMessage());
    }


    private String extractServiceName(String errorMessage) {
        if (errorMessage == null) {
            return "unknown";
        }

        // 匹配类似 "for the service machine-iam-service" 的部分
        Pattern pattern = Pattern.compile("for the service ([a-zA-Z0-9-]+)");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "unknown";
    }
}
