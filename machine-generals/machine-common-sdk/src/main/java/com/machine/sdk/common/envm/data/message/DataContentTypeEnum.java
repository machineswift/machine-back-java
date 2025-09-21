package com.machine.sdk.common.envm.data.message;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataContentTypeEnum implements BaseEnum<DataContentTypeEnum, String> {

    STRING("STRING", "字符串"),
    VAR("VAR", "变量");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}