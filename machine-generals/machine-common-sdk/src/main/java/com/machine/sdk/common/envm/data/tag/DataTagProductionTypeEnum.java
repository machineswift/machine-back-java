package com.machine.sdk.common.envm.data.tag;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataTagProductionTypeEnum implements BaseEnum<DataTagProductionTypeEnum, String> {
    STATISTICAL("STATISTICAL", "统计类"),
    RULE_BASED("RULE_BASED", "规则类"),
    MINING("MINING", "挖掘类"),
    IMPORTED("IMPORTED", "导入类");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}