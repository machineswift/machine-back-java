package com.machine.sdk.common.envm.iam.role;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamSupplierDefaultRoleEnum implements BaseEnum<IamSupplierDefaultRoleEnum, String> {
    SUPPLIER("SUPPLIER", "供应商"),
    EMPLOYEE("EMPLOYEE", "员工");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}