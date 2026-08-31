package com.machine.sdk.base.envm.biam.role;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamUserRoleBusinessTypeEnum implements BaseEnum<BIamUserRoleBusinessTypeEnum, String> {
    SHOP("SHOP", "门店");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
