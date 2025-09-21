package com.machine.sdk.beisen.envm;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeiSenGenderEnum implements BaseEnum<StatusEnum, String> {
    MALE("0", "男"),
    FEMALE("1", "女"),
    UNDEFINED("-1","未知");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
