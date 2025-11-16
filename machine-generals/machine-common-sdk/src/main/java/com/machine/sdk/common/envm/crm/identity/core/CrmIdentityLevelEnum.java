package com.machine.sdk.common.envm.crm.identity.core;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份等级
 */
@Getter
@AllArgsConstructor
public enum CrmIdentityLevelEnum implements BaseEnum<CrmIdentityLevelEnum, String> {

    L0("L0", "匿名用户"),
    L1("L1", "弱实名"),
    L2("L2", "强实名"),
    L3("L3", "高级认证"),
    L4("L4", "系统用户");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}