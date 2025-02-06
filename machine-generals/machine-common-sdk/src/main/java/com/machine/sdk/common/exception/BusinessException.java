package com.machine.sdk.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code,
                             String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code,
                             Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String code,
                             String message,
                             Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
