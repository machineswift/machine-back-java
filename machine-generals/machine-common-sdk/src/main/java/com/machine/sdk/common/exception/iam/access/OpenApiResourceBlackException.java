package com.machine.sdk.common.exception.iam.access;

import org.springframework.security.access.AccessDeniedException;

public class OpenApiResourceBlackException extends AccessDeniedException {
    public OpenApiResourceBlackException(String message) {
        super(message);
    }
}
