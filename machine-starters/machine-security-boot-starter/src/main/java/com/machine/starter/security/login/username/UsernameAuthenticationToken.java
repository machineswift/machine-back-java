package com.machine.starter.security.login.username;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

/**
 * 传输登录认证的数据的载体
 */
@Setter
@Getter
public class UsernameAuthenticationToken extends AbstractAuthenticationToken {

    private String username;
    private String password;
    private String captcha;
    private String userKey;

    public UsernameAuthenticationToken() {
        super(Collections.emptyList());
    }

    /**
     * 根据SpringSecurity的设计:
     * 授权成后，Credential（比如，登录密码）信息需要被清空。
     */
    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : password;
    }

    /**
     * 根据SpringSecurity的设计:
     * 授权成功之前，getPrincipal返回的客户端传过来的数据。
     * 授权成功后，返回当前登陆用户的信息。
     */
    @Override
    public Object getPrincipal() {
        return username;
    }

}
