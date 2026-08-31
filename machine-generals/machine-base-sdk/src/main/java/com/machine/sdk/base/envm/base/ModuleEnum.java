package com.machine.sdk.base.envm.base;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块
 */
@Getter
@AllArgsConstructor
public enum ModuleEnum implements BaseEnum<ModuleEnum, String> {
    PIAM("PIAM", "平台身份与访问管理"),
    BIAM("BIAM", "业务身份与访问管理"),
    CIAM("CIAM", "客户身份与访问管理"),
    DATA("DATA", "数据中心"),
    HRM("HRM", "人力资源"),
    SCM("SCM", "供应链"),
    CRM("CRM", "客户关系管理"),
    AI("AI", "人工智能");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
