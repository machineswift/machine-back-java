package com.machine.starter.security.exception;

import org.springframework.security.core.AuthenticationException;

public class PhoneNotFoundException extends AuthenticationException {
    public PhoneNotFoundException(String message) {
        super(message);
    }
}