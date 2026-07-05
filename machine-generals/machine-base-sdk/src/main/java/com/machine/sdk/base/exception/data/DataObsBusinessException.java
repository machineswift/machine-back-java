package com.machine.sdk.base.exception.data;


import com.machine.sdk.base.exception.BusinessException;

public class DataObsBusinessException extends BusinessException {

    public DataObsBusinessException(String code,
                                    String message) {
        super(code, message);
    }

    public DataObsBusinessException(String code,
                                    String message,
                                    Throwable cause) {
        super(code, message, cause);
    }
}
