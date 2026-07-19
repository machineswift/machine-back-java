package com.machine.sdk.base.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenInvalidException extends AuthenticationException {
    public AuthTokenInvalidException(String message) {
        super(message);
    }
}
