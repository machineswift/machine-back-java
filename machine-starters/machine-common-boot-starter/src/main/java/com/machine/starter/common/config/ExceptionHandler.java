package com.machine.starter.common.config;

import com.machine.sdk.common.exception.BusinessException;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceBlackException;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceClientException;
import com.machine.sdk.common.exception.iam.authentication.*;
import com.machine.sdk.common.model.AppResult;
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
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenExpireException.class)
    private AppResult<Objects> errorHandler(AuthTokenExpireException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.authTokenExpire", exception.getMessage());
    }

    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenParsingException.class)
    private AppResult<Objects> errorHandler(AuthTokenParsingException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.authTokenParsingFailed", exception.getMessage());
    }


    /**
     * AuthToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthTokenUseException.class)
    private AppResult<Objects> errorHandler(AuthTokenUseException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.authTokenUseWrong", exception.getMessage());
    }

    /**
     * RefreshToken使用异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = RefreshTokenUseException.class)
    private AppResult<Objects> errorHandler(RefreshTokenUseException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.refreshTokenUseWrong", exception.getMessage());
    }

    /**
     * JwtToken黑名单异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = JwtTokenBlackException.class)
    private AppResult<Objects> errorHandler(JwtTokenBlackException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.jwtTokenBlack", exception.getMessage());
    }

    /**
     * 用户禁用
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserStatusDisableException.class)
    private AppResult<Objects> errorHandler(UserStatusDisableException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication.userStatusDisable", exception.getMessage());
    }


    /**
     * 认证异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthenticationException.class)
    private AppResult<Objects> errorHandler(AuthenticationException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.UNAUTHORIZED.value(), "iam.auth.authentication", exception.getMessage());
    }

    /**
     * OpenApi黑名单异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = OpenApiResourceBlackException.class)
    private AppResult<Objects> errorHandler(OpenApiResourceBlackException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.FORBIDDEN.value(), "iam.auth.accessDenied.openApiResourceBlack", exception.getMessage());
    }

    /**
     * OpenApi没有带clientId
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = OpenApiResourceClientException.class)
    private AppResult<Objects> errorHandler(OpenApiResourceClientException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.FORBIDDEN.value(), "iam.auth.accessDenied.wrongClientId", exception.getMessage());
    }

    /**
     * 权限异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccessDeniedException.class)
    private AppResult<Objects> errorHandler(AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.FORBIDDEN.value(), "iam.auth.accessDenied", exception.getMessage());
    }

    /**
     * 参数异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    private AppResult<Objects> errorHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        List<String> errorMessages = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        String combinedMessage = String.join(", ", errorMessages);

        return AppResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "param.valid.exception", combinedMessage);
    }

    /**
     * 业务异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    private AppResult<Objects> errorHandler(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getCode(), exception.getMessage());
    }

    /**
     * 500异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public AppResult<Objects> errorHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "exception", exception.getMessage());
    }
}
