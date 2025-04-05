package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleBusinessTypeEnum implements BaseEnum<UserRoleBusinessTypeEnum, String> {
    SHOP("SHOP", "门店");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
