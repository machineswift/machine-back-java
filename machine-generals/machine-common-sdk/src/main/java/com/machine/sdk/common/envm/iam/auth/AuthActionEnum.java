package com.machine.sdk.common.envm.iam.auth;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证动作
 */
@Getter
@AllArgsConstructor
public enum AuthActionEnum implements BaseEnum<AuthActionEnum, String> {
    LOGIN("LOGIN", "登录"),
    REFRESH_TOKEN("REFRESH_TOKEN", "REFRESH_TOKEN"),
    LOGOUT("LOGOUT", "注销"),
    UNBIND_ACCOUNT("UNBIND_ACCOUNT", "解绑账号"),
    USER_CHANGE_PASSWORD("USER_CHANGE_PASSWORD", "用户修改密码"),
    ADMIN_CHANGE_PASSWORD("ADMIN_CHANGE_PASSWORD", "管理员修改密码"),
    PHONE_CAPTCHA_CHANGE_PASSWORD("PHONE_CAPTCHA_CHANGE_PASSWORD", "手机号验证码修改密码"),
    ;

    private final String code;
    private final String msg;

    @Override
    public String getName() {
        return this.name();
    }
}
