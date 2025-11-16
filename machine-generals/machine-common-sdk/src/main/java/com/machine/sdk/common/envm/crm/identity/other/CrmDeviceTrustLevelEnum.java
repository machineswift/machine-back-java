package com.machine.sdk.common.envm.crm.identity.other;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备信任等级枚举
 */
@Getter
@AllArgsConstructor
public enum CrmDeviceTrustLevelEnum implements BaseEnum<CrmDeviceTrustLevelEnum, String> {
    UNKNOWN("UNKNOWN", "未知"),
    LOW("LOW", "低信任"),
    MEDIUM("MEDIUM", "中等信任"),
    HIGH("HIGH", "高信任"),
    TRUSTED("TRUSTED", "可信设备");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
