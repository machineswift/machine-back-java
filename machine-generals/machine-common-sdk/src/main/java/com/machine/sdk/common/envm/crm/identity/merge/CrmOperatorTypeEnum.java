package com.machine.sdk.common.envm.crm.identity.merge;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作者类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmOperatorTypeEnum implements BaseEnum<CrmOperatorTypeEnum, String> {
    SYSTEM("SYSTEM", "系统"),
    ADMIN("ADMIN", "管理员"),
    USER("USER", "用户"),
    AI("AI", "人工智能");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}