package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenUseException extends AuthenticationException {
    public AuthTokenUseException(String message) {
        super(message);
    }
}
