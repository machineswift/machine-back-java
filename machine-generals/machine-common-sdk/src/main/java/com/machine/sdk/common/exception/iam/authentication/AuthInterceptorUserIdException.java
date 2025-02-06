package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthInterceptorUserIdException extends AuthenticationException {
    public AuthInterceptorUserIdException(String msg) {
        super(msg);
    }
}
