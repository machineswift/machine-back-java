package com.machine.sdk.base.exception.ai;

import com.machine.sdk.base.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AiBusinessException extends BusinessException {

    public AiBusinessException(String code,
                               String message) {
        super(code, message);
    }

    public AiBusinessException(String code,
                               String message,
                               Throwable cause) {
        super(code, message, cause);
    }

}
