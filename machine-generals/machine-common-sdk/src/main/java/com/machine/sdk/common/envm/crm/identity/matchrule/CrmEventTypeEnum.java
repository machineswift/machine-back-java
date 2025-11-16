package com.machine.sdk.common.envm.crm.identity.matchrule;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 事件类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmEventTypeEnum implements BaseEnum<CrmEventTypeEnum, String> {
    IDENTITY_CREATED("identity.created", "身份创建"),
    IDENTITY_MERGED("identity.merged", "身份合并"),
    IDENTITY_VERIFIED("identity.verified", "身份验证"),
    IDENTITY_UPDATED("identity.updated", "身份更新"),
    IDENTITY_DELETED("identity.deleted", "身份删除"),
    PROFILE_UPDATED("profile.updated", "档案更新"),
    LOGIN("login", "用户登录"),
    LOGOUT("logout", "用户登出");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
