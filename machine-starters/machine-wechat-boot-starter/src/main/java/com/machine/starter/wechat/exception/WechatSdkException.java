package com.machine.starter.wechat.exception;

import com.machine.sdk.common.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WechatSdkException extends BusinessException {

    public WechatSdkException(String code,
                              String message) {
        super(code, message);
    }

    public WechatSdkException(String code,
                              String message,
                              Throwable cause) {
        super(code, message, cause);
    }

}
