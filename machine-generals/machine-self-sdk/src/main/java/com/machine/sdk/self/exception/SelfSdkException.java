package com.machine.sdk.self.exception;

import com.machine.sdk.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SelfSdkException extends BusinessException {

    public SelfSdkException(String code,
                            String message) {
        super(code, message);
    }

    public SelfSdkException(String code,
                            String message,
                            Throwable cause) {
        super(code, message, cause);
    }

}
