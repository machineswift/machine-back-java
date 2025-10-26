package com.machine.sdk.beisen.envm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeiSenDepartmentStatusEnum implements BaseEnum<BeiSenDepartmentStatusEnum, String> {
    DISABLE("0", "停用"),
    ENABLE("1", "启用");
    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
