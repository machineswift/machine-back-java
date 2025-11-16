package com.machine.sdk.common.envm.crm.identity.risk;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 风险等级枚举
 */
@Getter
@AllArgsConstructor
public enum CrmRiskLevelEnum implements BaseEnum<CrmRiskLevelEnum, String> {
    LOW("LOW", "低风险"),
    MEDIUM("MEDIUM", "中风险"),
    HIGH("HIGH", "高风险"),
    CRITICAL("CRITICAL", "严重风险");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}