package com.machine.sdk.common.envm.crm.identity.channel;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 安全等级枚举
 */
@Getter
@AllArgsConstructor
public enum CrmSecurityLevelEnum implements BaseEnum<CrmSecurityLevelEnum, String> {
    LOW("LOW", "低安全"),
    STANDARD("STANDARD", "标准安全"),
    HIGH("HIGH", "高安全"),
    CRITICAL("CRITICAL", "关键安全");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}