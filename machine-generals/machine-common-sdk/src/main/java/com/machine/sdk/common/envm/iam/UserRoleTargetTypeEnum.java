package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleTargetTypeEnum implements BaseEnum<UserRoleTargetTypeEnum, String> {
    ORGANIZATION("ORGANIZATION", "组织"),
    SHOP("SHOP", "门店"),
    COMPANY("COMPANY", "公司");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
