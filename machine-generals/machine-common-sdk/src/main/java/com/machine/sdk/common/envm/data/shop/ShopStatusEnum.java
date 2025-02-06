package com.machine.sdk.common.envm.data.shop;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopStatusEnum implements BaseEnum<ShopStatusEnum, String> {
    UNDER_CONSTRUCTION("UNDER_CONSTRUCTION", "筹建中"),
    PENDING_OPENING("PENDING_OPENING", "待开业"),
    OPERATIONAL("OPERATIONAL", "营业中"),
    TEMPORARILY_CLOSED("OPERATIONAL", "临时闭店"),
    PERMANENTLY_CLOSED("PERMANENTLY_CLOSED", "永久闭店");


    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
