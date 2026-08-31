package com.machine.starter.security.config;

public class SecurityConstant {

    /**
     * 过期时间1小时
     */
    public static final long AUTH_TOKEN_EXPIRE_TIMESTAMP = 60 * 60 * 1000L;

    /**
     * 过期时间90天
     */
    public static final long REFRESH_TOKEN_EXPIRE_TIMESTAMP = 90 * 24 * 60 * 60 * 1000L;

    public static final String BEARER_TYPE = "Bearer";
    public static final String BEARER_TYPE_WITH_SPACE = "Bearer ";

    public static final String AUTH_TOKEN_ACCESS_TOKEN_ID_KEY = "tokenId";

    public static final String AUTH_TOKEN_REFRESH_TOKEN_KEY = "REFRESH_TOKEN";

    /**
     * 获取当前用户的接口路径
     */
    public static final String CURRENT_USER_PATH = "/machine-iam-app/iam/biam/authentication/internal/user_info";

    /**
     * 授权服务器登录页
     */
    public static final String AUTH_SERVER_LOGIN_PAGE = "/iam/biam/oauth2/authServer/login";

    /**
     * 授权服务器授权确认页
     */
    public static final String AUTH_SERVER_CONSENT_PAGE = "/iam/biam/oauth2/authServer/consent";

    /**
     * 授权服务器授权端点
     */
    public static final String AUTH_SERVER_AUTHORIZE_ENDPOINT = "/iam/biam/oauth2/authServer/authorize";

    /**
     * 授权服务器 OIDC UserInfo 端点
     */
    public static final String AUTH_SERVER_USERINFO_ENDPOINT = "/iam/biam/oauth2/authServer/userinfo";

    /**
     * 授权服务器 OIDC RP-Initiated Logout 端点
     */
    public static final String AUTH_SERVER_LOGOUT_ENDPOINT = "/iam/biam/oauth2/authServer/connect/logout";

    /**
     * 授权服务器令牌端点
     */
    public static final String AUTH_SERVER_TOKEN_ENDPOINT = "/iam/biam/oauth2/authServer/token";

    /**
     * 授权服务器令牌自省端点
     */
    public static final String AUTH_SERVER_INTROSPECT_ENDPOINT = "/iam/biam/oauth2/authServer/introspect";

    /**
     * 授权服务器令牌吊销端点
     */
    public static final String AUTH_SERVER_REVOKE_ENDPOINT = "/iam/biam/oauth2/authServer/revoke";

    /**
     * 授权服务器 JWKS 端点
     */
    public static final String AUTH_SERVER_JWKS_ENDPOINT = "/iam/biam/oauth2/authServer/jwks";

}
