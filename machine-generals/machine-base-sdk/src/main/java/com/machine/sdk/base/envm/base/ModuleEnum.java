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
    IAM("IAM", "身份管理"),
    DATA("DATA", "数据中心"),
    HRM("HRM", "人力资源"),
    SCM("SCM", "供应链");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
