package com.machine.sdk.common.envm.iam.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Auth2.0 认证来源
 */
@Getter
@AllArgsConstructor
public enum IamAuth2SourceEnum implements BaseEnum<IamAuth2SourceEnum, String> {
    GITHUB("GITHUB", "Github"),
    WEIBO("WEIBO", "新浪微博"),
    GITEE("GITEE", "码云"),
    FEISHU("FEISHU","飞书"),
    DINGTALK("DINGTALK", "钉钉扫码登录"),
    DINGTALK_ACCOUNT("DINGTALK_ACCOUNT", "钉钉账号登录"),
    WECHAT_OPEN("WECHAT_OPEN", "微信开放平台"),
    WECHAT_MP("WECHAT_MP", "微信公众平台"),
    ALIPAY("ALIPAY", "支付宝"),
    QQ("QQ", "QQ");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
