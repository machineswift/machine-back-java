package com.machine.sdk.common.envm.system;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataPersistenceStatusEnum implements BaseEnum<DataPersistenceStatusEnum, String> {
    TEMPORARY("TEMPORARY", "临时"),
    PERMANENT("PERMANENT", "永久");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}