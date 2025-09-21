package com.machine.sdk.common.exception.data;


import com.machine.sdk.common.exception.BusinessException;

public class DataLeafBusinessException extends BusinessException {

    public DataLeafBusinessException(String code,
                                     String message) {
        super(code, message);
    }

    public DataLeafBusinessException(String code,
                                     String message,
                                     Throwable cause) {
        super(code, message, cause);
    }
}
