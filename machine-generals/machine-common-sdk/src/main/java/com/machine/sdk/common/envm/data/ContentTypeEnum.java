package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentTypeEnum implements BaseEnum<ContentTypeEnum, String> {

    STRING("STRING", "字符串"),
    VAR("VAR", "变量");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}