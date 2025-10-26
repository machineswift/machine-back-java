package com.machine.starter.security;

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

    public static final String AUTH_TOKEN_HEADER_KEY = "Authorization";

    public static final String AUTH_TOKEN_ACCESS_TOKEN_ID_KEY = "tokenId";

    public static final String AUTH_TOKEN_REFRESH_TOKEN_KEY = "REFRESH_TOKEN";

    /**
     * 获取当前用户的接口路径
     */
    public static final String CURRENT_USER_PATH = "/machine-iam-app/iam/current/user_info";

}
