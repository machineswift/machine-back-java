package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserDepartmentRelationTypeEnum implements BaseEnum<UserDepartmentRelationTypeEnum, String> {
    LEADER("LEADER", "负责人"),
    MEMBER("MEMBER", "成员");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
