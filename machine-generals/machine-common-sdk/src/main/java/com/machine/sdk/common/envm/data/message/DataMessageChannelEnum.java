package com.machine.sdk.common.envm.data.message;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMessageChannelEnum implements BaseEnum<DataMessageChannelEnum, String> {

    SYSTEM_MESSAGE("SYSTEM_MESSAGE","站内信"),
    SUPER_APP_PUSH("SUPER_APP_PUSH","appPush"),
    ENTERPRISE_WECHAT("ENTERPRISE_WECHAT","企微工作通知"),
    FEI_SHU_ROBOT("FEI_SHU_ROBOT","飞书机器人"),
    SMS("SMS","短信");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
