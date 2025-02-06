package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionTypeEnum implements BaseEnum<PermissionTypeEnum, String> {
    READ("READ", "可访问"),
    GRANT("GRANT", "可授权");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}