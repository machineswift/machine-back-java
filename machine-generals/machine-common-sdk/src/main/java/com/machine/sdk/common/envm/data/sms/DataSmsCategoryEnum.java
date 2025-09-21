package com.machine.sdk.common.envm.data.sms;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信发送分类
 */
@Getter
@AllArgsConstructor
public enum DataSmsCategoryEnum implements BaseEnum<DataSmsCategoryEnum, String> {
    IAM_AUTH("IAM_AUTH", "认证"),
    ;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
