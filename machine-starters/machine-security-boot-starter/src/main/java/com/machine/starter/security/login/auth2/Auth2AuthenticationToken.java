package com.machine.starter.security.login.auth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Setter
@Getter
public class Auth2AuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 访问AuthorizeUrl后回调时带的参数code
     */
    private String code;

    /**
     * 访问AuthorizeUrl后回调时带的参数state，用于和请求AuthorizeUrl前的state比较，防止CSRF攻击
     */
    private String state;

    /**
     * 华为授权登录接受code的参数名
     *
     * @since 1.10.0
     */
    private String authorization_code;

    /**
     * Twitter回调后返回的oauth_token
     *
     * @since 1.13.0
     */
    private String oauth_token;

    /**
     * Twitter回调后返回的oauth_verifier
     *
     * @since 1.13.0
     */
    private String oauth_verifier;

    /**
     * 苹果仅在用户首次授权应用程序时返回此值。如果您的应用程序已经获得了用户的授权，那么苹果将不会再次返回此值
     *
     * @see <a href="https://developer.apple.com/documentation/sign_in_with_apple/useri">user info</a>
     */
    private String user;

    /**
     * 苹果错误信息，仅在用户取消授权时返回此值
     *
     * @see <a href="https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_js/incorporating_sign_in_with_apple_into_other_platforms">error response</a>
     */
    private String error;

    /**
     * 访问AuthorizeUrl后回调时带的参数auth_code，该参数目前只使用于支付宝登录
     */
    private String auth_code;

    public Auth2AuthenticationToken() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return isAuthenticated() ? null : code;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated() ? null : code;
    }

}
