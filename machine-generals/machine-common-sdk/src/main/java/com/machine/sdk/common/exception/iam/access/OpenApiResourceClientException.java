package com.machine.sdk.common.exception.iam.access;

import org.springframework.security.access.AccessDeniedException;

public class OpenApiResourceClientException extends AccessDeniedException {
    public OpenApiResourceClientException(String message) {
        super(message);
    }
}
