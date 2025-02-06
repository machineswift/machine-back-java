package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工作经验
 */
@Getter
@AllArgsConstructor
public enum WorkExperienceTypeEnum implements BaseEnum<WorkExperienceTypeEnum, String> {
    START_UP_BUSINESS("START_UP_BUSINESS", "创业族"),
    OFFICE_WORKER("OFFICE_WORKER", "上班族"),
    WAIT_FOR_EMPLOYMENT("WAIT_FOR_EMPLOYMENT", "待业");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
