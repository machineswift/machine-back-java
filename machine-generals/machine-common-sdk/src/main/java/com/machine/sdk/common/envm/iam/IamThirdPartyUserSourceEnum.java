package com.machine.sdk.common.envm.iam;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IamThirdPartyUserSourceEnum implements BaseEnum<IamThirdPartyUserSourceEnum, String> {
    HUA_WEI("HUA_WEI","华为"),
    APPLE("APPLE", "苹果"),
    WECHAT_OPEN("WECHAT_OPEN", "微信开放平台"),
    ENTERPRISE_WECHAT("ENTERPRISE_WECHAT", "企业微信"),
    QQ("QQ", "QQ"),
    DING_TALK("DING_TALK", "钉钉"),
    ALI_YUN("ALI_YUN", "阿里云"),
    ALI_PAY("ALI_PAY", "支付宝"),
    WEI_BO("WEI_BO", "微博"),
    GIT_EE("GIT_EE", "码云"),
    GITHUB("GITHUB", "GITHUB"),
    FEI_SHU("FEI_SHU", "飞书"),
    DOU_YIN("DOU_YIN", "抖音");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
