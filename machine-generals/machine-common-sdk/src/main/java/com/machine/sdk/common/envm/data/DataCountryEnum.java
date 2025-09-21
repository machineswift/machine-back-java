package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataCountryEnum implements BaseEnum<DataCountryEnum, String> {
    CHINA("CHINA", "中国"),
    AMERICAN("AMERICAN", "美国");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}