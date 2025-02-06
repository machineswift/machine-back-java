package com.machine.sdk.common.exception.sdk;

import com.machine.sdk.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SdkBusinessException extends BusinessException {

    public SdkBusinessException(String code,
                                String message) {
        super(code, message);
    }

    public SdkBusinessException(String code,
                                String message,
                                Throwable cause) {
        super(code, message, cause);
    }

}
