package com.machine.sdk.common.envm.data.tag;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileSubjectTypeEnum implements BaseEnum<ProfileSubjectTypeEnum, String> {
    CUSTOMER("CUSTOMER", "客户"),
    MEMBER("MEMBER", "会员"),
    SHOP("SHOP", "门店"),
    ITEM("ITEM", "商品");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}