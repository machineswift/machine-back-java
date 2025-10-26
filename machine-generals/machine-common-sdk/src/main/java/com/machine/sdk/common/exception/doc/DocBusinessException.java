package com.machine.sdk.common.exception.doc;


import com.machine.sdk.common.exception.BusinessException;

public class DocBusinessException extends BusinessException {

    public DocBusinessException(String code,
                                String message) {
        super(code, message);
    }

    public DocBusinessException(String code,
                                String message,
                                Throwable cause) {
        super(code, message, cause);
    }
}
