package com.machine.sdk.base.envm.scm.product;

import com.machine.sdk.base.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品销售状态（上架/下架）
 */
@Getter
@AllArgsConstructor
public enum ScmProductSaleStatusEnum implements BaseEnum<ScmProductSaleStatusEnum, String> {
    /** 上架（可售） */
    ON_SALE("ON_SALE", "上架"),
    /** 下架（人工） */
    OFF_SHELF("OFF_SHELF", "下架"),
    /** 系统下架（规则/风控触发） */
    AUTO_OFF("AUTO_OFF", "系统下架");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
