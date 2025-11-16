package com.machine.sdk.common.envm.iam.role;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamShopDefaultRoleEnum implements BaseEnum<IamShopDefaultRoleEnum, String> {
    FRANCHISEE("FRANCHISEE", "加盟商"),
    STORE_MANAGER("STORE_MANAGER", "店长"),
    SALES_CLERK("SALES_CLERK", "店员");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}