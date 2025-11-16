package com.machine.sdk.common.envm.crm.identity.other;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 归档原因枚举
 */
@Getter
@AllArgsConstructor
public enum CrmArchiveReasonEnum implements BaseEnum<CrmArchiveReasonEnum, String> {
    MERGE("MERGE", "身份合并"),
    DELETE("DELETE", "数据删除"),
    COMPLIANCE("COMPLIANCE", "合规要求"),
    RETENTION("RETENTION", "保留策略");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}