package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyEmployeeStatusEnum implements BaseEnum<CompanyEmployeeStatusEnum, String> {
    FULL_TIME("FULL_TIME", "正式"),
    LEFT("LEFT", "离职");


    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
