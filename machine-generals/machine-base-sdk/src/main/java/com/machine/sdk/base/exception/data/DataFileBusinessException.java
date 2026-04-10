package com.machine.sdk.base.exception.data;


import com.machine.sdk.base.exception.BusinessException;

public class DataFileBusinessException extends BusinessException {

    public DataFileBusinessException(String code,
                                     String message) {
        super(code, message);
    }

    public DataFileBusinessException(String code,
                                     String message,
                                     Throwable cause) {
        super(code, message, cause);
    }
}
