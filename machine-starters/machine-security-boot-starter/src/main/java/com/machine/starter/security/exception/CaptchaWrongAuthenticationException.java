package com.machine.starter.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaWrongAuthenticationException extends AuthenticationException {
    public CaptchaWrongAuthenticationException(String msg) {
        super(msg);
    }
}