package com.machine.sdk.common.envm.crm.identity.relation;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关系状态枚举
 */
@Getter
@AllArgsConstructor
public enum CrmRelationStatusEnum implements BaseEnum<CrmRelationStatusEnum, String> {
    ACTIVE("ACTIVE", "活跃"),
    INACTIVE("INACTIVE", "非活跃"),
    SUSPENDED("SUSPENDED", "暂停"),
    EXPIRED("EXPIRED", "已过期");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}