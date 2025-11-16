package com.machine.sdk.common.envm.crm.identity.other;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 手机号变更类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmPhoneChangeTypeEnum implements BaseEnum<CrmPhoneChangeTypeEnum, String> {
    USER_CHANGE("USER_CHANGE", "用户主动变更"),
    ADMIN_CHANGE("ADMIN_CHANGE", "管理员变更"),
    RECYCLE("RECYCLE", "号码回收"),
    SECURITY("SECURITY", "安全变更");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}