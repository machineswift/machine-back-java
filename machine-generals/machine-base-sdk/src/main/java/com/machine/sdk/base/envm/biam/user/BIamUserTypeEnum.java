package com.machine.sdk.base.envm.biam.user;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamUserTypeEnum implements BaseEnum<BIamUserTypeEnum, String> {
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
