package com.machine.sdk.base.envm.data.sms;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信发送结果
 */
@Getter
@AllArgsConstructor
public enum DataSmsSendResultEnum implements BaseEnum<DataSmsSendResultEnum, String> {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    ;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
