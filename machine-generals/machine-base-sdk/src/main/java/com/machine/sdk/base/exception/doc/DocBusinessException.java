package com.machine.sdk.base.exception.doc;


import com.machine.sdk.base.exception.BusinessException;

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
