package com.machine.sdk.base.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenExpireException extends AuthenticationException {
    public AuthTokenExpireException(String message) {
        super(message);
    }
}
