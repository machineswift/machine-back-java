package com.machine.sdk.base.exception.biam;

import com.machine.sdk.base.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BIamBusinessException extends BusinessException {

    public BIamBusinessException(String code,
                                 String message) {
        super(code, message);
    }

    public BIamBusinessException(String code,
                                 String message,
                                 Throwable cause) {
        super(code, message, cause);
    }

}
