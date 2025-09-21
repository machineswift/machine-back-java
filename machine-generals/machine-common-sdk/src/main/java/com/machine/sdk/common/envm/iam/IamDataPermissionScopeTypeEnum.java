package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamDataPermissionScopeTypeEnum implements BaseEnum<IamDataPermissionScopeTypeEnum, String> {
    ALL("ALL", "全部数据"),
    ORG_AND_SUB("ORG_AND_SUB", "本组织及下级组织数据"),
    ORG("ORG", "本组织数据"),
    CUSTOM("CUSTOM", "自定义数据"),
    SELF("SELF", "仅本人数据");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
