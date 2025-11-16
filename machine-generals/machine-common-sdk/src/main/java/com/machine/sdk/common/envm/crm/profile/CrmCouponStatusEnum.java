package com.machine.sdk.common.envm.crm.profile;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券状态枚举
 */
@Getter
@AllArgsConstructor
public enum CrmCouponStatusEnum implements BaseEnum<CrmCouponStatusEnum, String> {
    UNUSED("UNUSED", "未使用"),
    USED("USED", "已使用"),
    EXPIRED("EXPIRED", "已过期"),
    INVALID("INVALID", "已失效"),
    FROZEN("FROZEN", "已冻结");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}