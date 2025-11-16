package com.machine.sdk.common.envm.crm.identity.matchrule;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 匹配规则类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmMatchRuleTypeEnum implements BaseEnum<CrmMatchRuleTypeEnum, String> {
    EXACT("EXACT", "精确匹配"),
    FUZZY("FUZZY", "模糊匹配"),
    BEHAVIOR("BEHAVIOR", "行为匹配"),
    COMPOSITE("COMPOSITE", "复合匹配");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
