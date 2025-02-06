package com.machine.sdk.common.exception.iam;


import com.machine.sdk.common.exception.BusinessException;

public class IamAuth2BusinessException extends BusinessException {

    public IamAuth2BusinessException(String code,
                                     String message) {
        super(code, message);
    }

    public IamAuth2BusinessException(String code,
                                     String message,
                                     Throwable cause) {
        super(code, message, cause);
    }
}
