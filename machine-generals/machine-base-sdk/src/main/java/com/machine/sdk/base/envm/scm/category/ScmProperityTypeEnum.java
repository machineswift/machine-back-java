package com.machine.sdk.base.envm.scm.category;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScmProperityTypeEnum implements BaseEnum<ScmProperityTypeEnum, String> {
    KEY("KEY", "关键"),
    SALE("SALE", "销售"),
    SPEC("SPEC", "描述");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
