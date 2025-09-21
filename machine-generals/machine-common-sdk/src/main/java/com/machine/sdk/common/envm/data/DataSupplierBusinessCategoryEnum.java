package com.machine.sdk.common.envm.data;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 供应商业务类别
 */
@Getter
@AllArgsConstructor
public enum DataSupplierBusinessCategoryEnum implements BaseEnum<DataSupplierBusinessCategoryEnum, String> {
    INTEGRATED_SUPPLIER("INTEGRATED_SUPPLIER", "统装供应商"),
    SELF_INSTALLATION_SUPPLIER("SELF_INSTALLATION_SUPPLIER", "自装供应商"),
    EQUIPMENT_SUPPLIER("EQUIPMENT_SUPPLIER", "设备供应商"),
    COMMODITY_SUPPLIER("COMMODITY_SUPPLIER}", "商品供应商");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }

}
