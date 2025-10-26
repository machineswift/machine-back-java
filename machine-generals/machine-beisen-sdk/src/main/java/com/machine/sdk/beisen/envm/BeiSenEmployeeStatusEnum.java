package com.machine.sdk.beisen.envm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeiSenEmployeeStatusEnum implements BaseEnum<BeiSenEmployeeStatusEnum, String> {
    PENDING_HIRE("1", "待入职"),
    PROBATION("2", "试用"),
    FULL_TIME("3", "正式"),
    TRANSFER_OUT("4", "调出"),
    PENDING_TRANSFER_IN("5", "待调入"),
    RETIRED("6", "退休"),
    LEFT("8", "离职"),
    INFORMAL("12", "非正式");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
