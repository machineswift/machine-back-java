package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工作经验
 */
@Getter
@AllArgsConstructor
public enum DataWorkExperienceTypeEnum implements BaseEnum<DataWorkExperienceTypeEnum, String> {
    START_UP_BUSINESS("START_UP_BUSINESS", "创业族"),
    OFFICE_WORKER("OFFICE_WORKER", "上班族"),
    WAIT_FOR_EMPLOYMENT("WAIT_FOR_EMPLOYMENT", "待业");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
