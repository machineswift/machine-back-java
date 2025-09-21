package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthFeignUserIdException extends AuthenticationException {
    public AuthFeignUserIdException(String message) {
        super(message);
    }
}
