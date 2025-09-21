package com.machine.sdk.common.envm.iam.organization;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamOrganizationTypeEnum implements BaseEnum<IamOrganizationTypeEnum, String> {
    OPERATIONS("OPERATIONS", "运营"),
    MARKETING("MARKETING", "市场"),
    TAKEOUT("TAKEOUT", "外卖");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
