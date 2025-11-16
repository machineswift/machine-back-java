package com.machine.sdk.common.envm.crm.identity.channel;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道类型枚举
 */
@Getter
@AllArgsConstructor
public enum CrmChannelTypeEnum implements BaseEnum<CrmChannelTypeEnum, String> {
    WECHAT_MINI("WECHAT_MINI", "微信小程序"),
    ALIPAY_MINI("ALIPAY_MINI", "支付宝小程序"),
    MOBILE_APP("MOBILE_APP", "手机APP"),
    WEB("WEB", "网站"),
    H5("H5", "H5页面"),
    OFFLINE("OFFLINE", "线下");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
