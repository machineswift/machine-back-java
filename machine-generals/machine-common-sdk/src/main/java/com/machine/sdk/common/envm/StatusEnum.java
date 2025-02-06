package com.machine.sdk.common.envm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum implements BaseEnum<StatusEnum, String> {
    DISABLE("DISABLE", "禁用"),
    ENABLE("ENABLE", "启用");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
