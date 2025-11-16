package com.machine.sdk.common.envm.iam.user;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamUserTypeEnum implements BaseEnum<IamUserTypeEnum, String> {
    COMPANY("COMPANY", "公司员工"),
    SHOP("SHOP", "门店员工"),
    FRANCHISEE("FRANCHISEE", "加盟商"),
    SUPPLIER("SUPPLIER", "供应商");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
