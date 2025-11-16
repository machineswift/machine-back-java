package com.machine.sdk.common.envm.crm.identity.verification;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生命周期阶段枚举
 */
@Getter
@AllArgsConstructor
public enum CrmLifecycleStageEnum implements BaseEnum<CrmLifecycleStageEnum, String> {
    NEW("NEW", "新用户"),
    ACTIVE("ACTIVE", "活跃用户"),
    AT_RISK("AT_RISK", "风险用户"),
    CHURNED("CHURNED", "流失用户"),
    RECOVERED("RECOVERED", "召回用户"),
    LOYAL("LOYAL", "忠诚用户");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}