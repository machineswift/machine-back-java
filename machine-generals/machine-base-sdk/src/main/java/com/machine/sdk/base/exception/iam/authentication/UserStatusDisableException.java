package com.machine.sdk.base.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class UserStatusDisableException extends AuthenticationException {
    public UserStatusDisableException(String message) {
        super(message);
    }
}
