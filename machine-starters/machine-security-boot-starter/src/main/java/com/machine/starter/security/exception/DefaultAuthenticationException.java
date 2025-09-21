package com.machine.starter.security.exception;

import org.springframework.security.core.AuthenticationException;

public class DefaultAuthenticationException extends AuthenticationException {
    public DefaultAuthenticationException(String message) {
        super(message);
    }
}
