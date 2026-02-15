package com.machine.sdk.common.envm.base;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StorageTypeEnum implements BaseEnum<StorageTypeEnum, String> {
    TEMPORARY("TEMPORARY", "临时"),
    PERMANENT("PERMANENT", "永久");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}