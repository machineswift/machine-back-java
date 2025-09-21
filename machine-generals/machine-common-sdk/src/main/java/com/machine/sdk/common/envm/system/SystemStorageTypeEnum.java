package com.machine.sdk.common.envm.system;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemStorageTypeEnum implements BaseEnum<SystemStorageTypeEnum, String> {
    TEMPORARY("TEMPORARY", "临时"),
    PERMANENT("PERMANENT", "永久");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}