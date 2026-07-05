package com.machine.sdk.base.exception.scm;

import com.machine.sdk.base.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScmBusinessException extends BusinessException {

    public ScmBusinessException(String code,
                                String message) {
        super(code, message);
    }

    public ScmBusinessException(String code,
                                String message,
                                Throwable cause) {
        super(code, message, cause);
    }

}
