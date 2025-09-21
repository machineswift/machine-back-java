package com.machine.sdk.common.envm.iam.organization;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamOrganizationSelectTypeEnum implements BaseEnum<IamOrganizationSelectTypeEnum, String> {
    SELF("SELF", "仅本级"),
    SELF_AND_SUB("SELF_AND_SUB", "本级及下级");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
