package com.machine.sdk.base.envm.scm.category;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScmAttributeGroupTypeEnum implements BaseEnum<ScmAttributeGroupTypeEnum, String> {
    NORMAL("NORMAL", "普通"),
    SKU("SKU", "销售"),
    EXTEND("EXTEND", "扩展");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
