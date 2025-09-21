package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenBlackException extends AuthenticationException {
    public JwtTokenBlackException(String message) {
        super(message);
    }
}
