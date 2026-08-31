package com.machine.sdk.base.envm.biam.role;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamRoleTypeEnum implements BaseEnum<BIamRoleTypeEnum, String> {
    COMPANY("COMPANY", "公司角色"),
    SHOP("SHOP", "门店角色"),
    SUPPLIER("SUPPLIER", "供应商角色"),
    OPENAPI("OPENAPI", "开放平台角色");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
