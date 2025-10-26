package com.machine.sdk.common.envm.common;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements BaseEnum<GenderEnum, String> {
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女"),
    UNDEFINED("UNDEFINED", "未知");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
