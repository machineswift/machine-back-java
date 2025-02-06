package com.machine.starter.security.login.sms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 传输登录认证的数据的载体
 */
@Setter
@Getter
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private String phone;
    private String captcha;

    public SmsAuthenticationToken() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : captcha;
    }

    /**
     * 根据SpringSecurity的设计:
     * 授权成功之前，getPrincipal返回的客户端传过来的数据。
     * 授权成功后，返回当前登陆用户的信息。
     */
    @Override
    public Object getPrincipal() {
        return isAuthenticated() ? null : phone;
    }

}
