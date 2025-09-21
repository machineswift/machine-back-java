package com.machine.sdk.common.envm.iam.role;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamRoleTypeEnum implements BaseEnum<StatusEnum, String> {
    COMPANY("COMPANY", "公司角色"),
    SHOP("SHOP", "门店角色"),
    SUPPLIER("SUPPLIER", "供应商角色");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
