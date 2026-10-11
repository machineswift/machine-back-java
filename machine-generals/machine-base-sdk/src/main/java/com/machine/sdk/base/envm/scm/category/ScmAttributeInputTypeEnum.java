package com.machine.sdk.base.envm.scm.category;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScmAttributeInputTypeEnum implements BaseEnum<ScmAttributeInputTypeEnum, String> {
    SELECT("SELECT", "选择"),
    NUMBER("NUMBER", "数值"),
    TEXT("TEXT", "文本");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
