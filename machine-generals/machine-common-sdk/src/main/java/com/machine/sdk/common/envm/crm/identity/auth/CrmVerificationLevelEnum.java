package com.machine.sdk.common.envm.crm.identity.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证等级枚举
 */
@Getter
@AllArgsConstructor
public enum CrmVerificationLevelEnum implements BaseEnum<CrmVerificationLevelEnum, String> {
    L0("L0", "未验证"),
    L1("L1", "弱验证"),
    L2("L2", "强验证");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}