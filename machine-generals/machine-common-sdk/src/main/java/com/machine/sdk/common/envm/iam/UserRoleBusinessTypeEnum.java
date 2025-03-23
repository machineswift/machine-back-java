package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleBusinessTypeEnum implements BaseEnum<UserRoleBusinessTypeEnum, String> {
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
