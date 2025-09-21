package com.machine.sdk.common.exception.crm;

import com.machine.sdk.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CrmBusinessException extends BusinessException {

    public CrmBusinessException(String code,
                                String message) {
        super(code, message);
    }

    public CrmBusinessException(String code,
                                String message,
                                Throwable cause) {
        super(code, message, cause);
    }

}
