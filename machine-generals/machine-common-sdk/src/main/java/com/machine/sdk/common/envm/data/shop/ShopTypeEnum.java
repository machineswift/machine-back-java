package com.machine.sdk.common.envm.data.shop;

import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopTypeEnum implements BaseEnum<ShopTypeEnum, String> {
    COMPANY_OWNED("COMPANY_OWNED", "直营"),
    FRANCHISE("FRANCHISE", "加盟"),
    LIVE_STREAMING("LIVE_STREAMING","直播");

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
