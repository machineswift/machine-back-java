package com.machine.sdk.common.envm.crm.identity.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 强身份标识类型
 */
@Getter
@AllArgsConstructor
public enum CrmStrongIdentityTypeEnum implements BaseEnum<CrmStrongIdentityTypeEnum, String> {

    PHONE("PHONE", "手机号"),
    EMAIL("EMAIL", "邮箱"),
    ID_CARD("ID_CARD", "身份证"),
    WECHAT_UNIONID("WECHAT_UNIONID", "微信UnionID"),
    ALIPAY_USERID("ALIPAY_USERID", "支付宝用户ID"),
    BANK_CARD("BANK_CARD", "银行卡");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}