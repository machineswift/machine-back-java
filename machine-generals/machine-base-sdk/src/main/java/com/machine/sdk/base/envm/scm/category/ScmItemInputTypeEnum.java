package com.machine.sdk.base.envm.scm.category;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScmItemInputTypeEnum implements BaseEnum<ScmItemInputTypeEnum, String> {
    SELECT("SELECT", "选择"),
    INPUT("INPUT", "输入");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
