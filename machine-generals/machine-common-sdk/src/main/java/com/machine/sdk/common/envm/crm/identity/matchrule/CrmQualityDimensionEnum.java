package com.machine.sdk.common.envm.crm.identity.matchrule;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据质量维度枚举
 */
@Getter
@AllArgsConstructor
public enum CrmQualityDimensionEnum implements BaseEnum<CrmQualityDimensionEnum, String> {
    COMPLETENESS("COMPLETENESS", "完整性"),
    ACCURACY("ACCURACY", "准确性"),
    CONSISTENCY("CONSISTENCY", "一致性"),
    TIMELINESS("TIMELINESS", "及时性"),
    VALIDITY("VALIDITY", "有效性");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}