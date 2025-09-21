package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenUseException extends AuthenticationException {
    public RefreshTokenUseException(String message) {
        super(message);
    }
}
