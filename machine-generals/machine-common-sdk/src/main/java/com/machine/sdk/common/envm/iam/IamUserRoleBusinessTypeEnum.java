package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamUserRoleBusinessTypeEnum implements BaseEnum<IamUserRoleBusinessTypeEnum, String> {
    SHOP("SHOP", "门店");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
