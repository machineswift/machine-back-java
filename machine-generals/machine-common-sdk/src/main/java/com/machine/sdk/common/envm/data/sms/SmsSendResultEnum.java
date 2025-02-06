package com.machine.sdk.common.envm.data.sms;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信发送结果
 */
@Getter
@AllArgsConstructor
public enum SmsSendResultEnum implements BaseEnum<SmsSendResultEnum, String> {
    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    ;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
