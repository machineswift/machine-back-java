package com.machine.sdk.common.exception.iam.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthTokenParsingException extends AuthenticationException {
    public AuthTokenParsingException(String msg) {
        super(msg);
    }
}
