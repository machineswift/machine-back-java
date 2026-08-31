package com.machine.sdk.base.envm.biam.role;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamSupplierDefaultRoleEnum implements BaseEnum<BIamSupplierDefaultRoleEnum, String> {
    SUPPLIER("SUPPLIER", "供应商"),
    EMPLOYEE("EMPLOYEE", "员工");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}