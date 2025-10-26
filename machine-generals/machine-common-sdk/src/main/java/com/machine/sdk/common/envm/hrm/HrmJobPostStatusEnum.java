package com.machine.sdk.common.envm.hrm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HrmJobPostStatusEnum implements BaseEnum<HrmJobPostStatusEnum, String> {
    DISABLE("DISABLE", "停用"),
    ENABLE("ENABLE", "启用");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
