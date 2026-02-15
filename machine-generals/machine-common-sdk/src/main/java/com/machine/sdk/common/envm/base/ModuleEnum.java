package com.machine.sdk.common.envm.base;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模块
 */
@Getter
@AllArgsConstructor
public enum ModuleEnum implements BaseEnum<ModuleEnum, String> {
    IAM("IAM", "身份管理"),
    DATA("DATA", "数据中心");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
