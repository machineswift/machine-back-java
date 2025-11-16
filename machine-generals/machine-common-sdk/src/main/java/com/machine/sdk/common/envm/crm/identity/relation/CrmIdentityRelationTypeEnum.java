package com.machine.sdk.common.envm.crm.identity.relation;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份关系类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmIdentityRelationTypeEnum implements BaseEnum<CrmIdentityRelationTypeEnum, String> {
    SAME_PERSON("SAME_PERSON", "同一人"),
    HOUSEHOLD("HOUSEHOLD", "家庭成员"),
    COLLEAGUE("COLLEAGUE", "同事"),
    DEVICE_SHARING("DEVICE_SHARING", "设备共享"),
    SAME_ORGANIZATION("SAME_ORGANIZATION", "同组织");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}

