package com.machine.sdk.common.envm.iam.organization;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamOrganizationTypeEnum implements BaseEnum<IamOrganizationTypeEnum, String> {
    MARKETING("MARKETING", "市场部组织"),
    OPERATIONS("OPERATIONS", "运营部组织"),
    TAKEOUT("TAKEOUT", "外卖部组织");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
