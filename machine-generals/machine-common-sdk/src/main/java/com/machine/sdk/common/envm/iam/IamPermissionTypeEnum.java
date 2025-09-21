package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamPermissionTypeEnum implements BaseEnum<IamPermissionTypeEnum, String> {
    READ("READ", "可访问"),
    GRANT("GRANT", "可授权");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}