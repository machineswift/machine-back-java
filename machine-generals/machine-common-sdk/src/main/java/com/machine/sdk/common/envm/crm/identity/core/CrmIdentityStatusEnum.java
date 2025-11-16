package com.machine.sdk.common.envm.crm.identity.core;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份实体状态
 */
@Getter
@AllArgsConstructor
public enum CrmIdentityStatusEnum implements BaseEnum<CrmIdentityStatusEnum, String> {

    ACTIVE("ACTIVE", "活跃"),
    INACTIVE("INACTIVE", "非活跃"),
    SUSPENDED("SUSPENDED", "暂停使用"),
    MERGED("MERGED", "已合并"),
    CLOSED("CLOSED", "已关闭"),
    ARCHIVED("ARCHIVED", "已归档"),
    LOCKED("LOCKED", "锁定状态"),
    PURGED("PURGED", "已物理删除");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
