package com.machine.sdk.common.envm.hrm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HrmEmployeeStatusEnum implements BaseEnum<HrmEmployeeStatusEnum, String> {
    FULL_TIME("FULL_TIME", "正式"),
    LEFT("LEFT", "离职");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
