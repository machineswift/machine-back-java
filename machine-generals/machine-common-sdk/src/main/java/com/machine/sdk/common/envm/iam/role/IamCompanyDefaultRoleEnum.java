package com.machine.sdk.common.envm.iam.role;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamCompanyDefaultRoleEnum implements BaseEnum<IamCompanyDefaultRoleEnum, String> {
    ROOT("ROOT", "超级管理员"),
    ADMIN("ADMIN", "管理员"),
    CHAIRMAN("CHAIRMAN", "董事长"),
    VICE_PRESIDENT("VICE_PRESIDENT", "副总"),
    GENERAL_MANAGER("GENERAL_MANAGER", "总经理"),
    ASSISTANT_MANAGER("ASSISTANT_MANAGER", "副经理"),
    DIRECTOR("DIRECTOR", "总监"),
    DEPUTY_DIRECTOR("DEPUTY_DIRECTOR", "副总监"),
    REGIONAL_DIRECTOR("REGIONAL_DIRECTOR", "大区总监"),
    BRANCH_MANAGER("BRANCH_MANAGER", "分公司负责人"),
    AREA_MANAGER("AREA_MANAGER", "区域经理"),
    MANAGER("MANAGER", "经理"),
    SUPERVISOR("SUPERVISOR", "主管"),
    INSPECTOR("INSPECTOR", "督导"),
    DESIGNER("DESIGNER", "设计师"),
    ENGINEER("ENGINEER", "工程师"),
    SPECIALIST("SPECIALIST", "专员"),
    GUEST("GUEST", "访客");

    @Override
    public String getName() {
        return this.name();
    }

    private final String code;
    private final String message;
}