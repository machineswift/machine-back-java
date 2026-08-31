package com.machine.sdk.base.envm.biam.permission;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BIamPermissionTypeEnum implements BaseEnum<BIamPermissionTypeEnum, String> {
    READ("READ", "可访问"),
    GRANT("GRANT", "可授权");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}