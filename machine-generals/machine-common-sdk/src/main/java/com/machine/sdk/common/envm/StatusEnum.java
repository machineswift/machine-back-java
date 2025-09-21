package com.machine.sdk.common.envm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum implements BaseEnum<StatusEnum, String> {
    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
