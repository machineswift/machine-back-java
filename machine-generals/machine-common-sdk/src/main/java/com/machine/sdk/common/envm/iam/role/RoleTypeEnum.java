package com.machine.sdk.common.envm.iam.role;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum implements BaseEnum<StatusEnum, String> {
    COMPANY("COMPANY", "公司内部角色"),
    SHOP("SHOP", "门店相关角色"),
    SUPPLIER("SUPPLIER", "供应商相关角色");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
