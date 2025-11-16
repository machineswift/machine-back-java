package com.machine.sdk.common.envm.crm.profile;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmCouponTypeEnum implements BaseEnum<CrmCouponTypeEnum, String> {
    DISCOUNT("DISCOUNT", "折扣券"),
    VOUCHER("VOUCHER", "代金券"),
    FREE_SHIPPING("FREE_SHIPPING", "免运费券"),
    GIFT("GIFT", "礼品券"),
    POINTS("POINTS", "积分券");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}