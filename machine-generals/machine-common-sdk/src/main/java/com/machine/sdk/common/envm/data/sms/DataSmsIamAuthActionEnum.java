package com.machine.sdk.common.envm.data.sms;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证触发短信的动作
 */
@Getter
@AllArgsConstructor
public enum DataSmsIamAuthActionEnum implements BaseEnum<DataSmsIamAuthActionEnum, String> {
//    ADMIN_RESET_PASSWORD("ADMIN_RESET_PASSWORD", "管理员重置密码"),
//    ADMIN_CHANGE_PASSWORD("ADMIN_CHANGE_PASSWORD", "管理员修改密码"),
    USER_FORGET_PASSWORD("USER_FORGET_PASSWORD", "用户忘记密码"),
    PHONE_CAPTCHA_LOGIN("PHONE_CAPTCHA_LOGIN", "手机号验证码登录"),
    ;

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
