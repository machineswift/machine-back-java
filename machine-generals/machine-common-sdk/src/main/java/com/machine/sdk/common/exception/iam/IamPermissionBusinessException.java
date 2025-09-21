package com.machine.sdk.common.exception.iam;

import com.machine.sdk.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IamPermissionBusinessException extends BusinessException {

    public IamPermissionBusinessException(String code,
                                          String message) {
        super(code, message);
    }

    public IamPermissionBusinessException(String code,
                                          String message,
                                          Throwable cause) {
        super(code, message, cause);
    }

}
