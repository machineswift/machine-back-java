package com.machine.sdk.common.envm.iam.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证方式(AuthDefaultSource)
 */
@Getter
@AllArgsConstructor
public enum AuthMethodEnum implements BaseEnum<AuthMethodEnum, String> {
    REFRESH_TOKEN("REFRESH_TOKEN", "REFRESH_TOKEN"),
    USERNAME_PASSWORD("USERNAME_PASSWORD", "用户名密码"),
    PHONE_CAPTCHA("PHONE_CAPTCHA", "手机号验证码"),
    AUTH2_GITEE("AUTH2_GITEE", "码云"),
    AUTH2_WECHAT_OPEN("AUTH2_WECHAT_OPEN", "微信开放平台"),
    AUTH2_FEI_SHU("AUTH2_FEI_SHU", "飞书"),
    NULL("NULL", "空"),;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
