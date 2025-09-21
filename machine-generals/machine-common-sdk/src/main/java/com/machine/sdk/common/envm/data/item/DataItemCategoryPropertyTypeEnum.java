package com.machine.sdk.common.envm.data.item;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataItemCategoryPropertyTypeEnum implements BaseEnum<DataItemCategoryPropertyTypeEnum, String> {
    KEY("KEY", "关键属性"),
    SALES("SALES", "销售属性"),
    DESCRIPTION("DESCRIPTION", "描述属性");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}