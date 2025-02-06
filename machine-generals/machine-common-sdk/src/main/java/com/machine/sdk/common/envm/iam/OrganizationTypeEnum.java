package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationTypeEnum implements BaseEnum<OrganizationTypeEnum, String> {
    OPERATIONS("OPERATIONS", "运营"),
    MARKETING("MARKETING", "市场"),
    TAKEOUT("TAKEOUT", "外卖");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
