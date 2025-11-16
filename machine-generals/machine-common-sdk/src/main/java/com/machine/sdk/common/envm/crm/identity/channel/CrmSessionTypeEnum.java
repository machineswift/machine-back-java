package com.machine.sdk.common.envm.crm.identity.channel;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmSessionTypeEnum implements BaseEnum<CrmSessionTypeEnum, String> {
    WEB("WEB", "网页会话"),
    MOBILE("MOBILE", "移动端会话"),
    CROSS_PLATFORM("CROSS_PLATFORM", "跨平台会话"),
    API("API", "API会话");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}