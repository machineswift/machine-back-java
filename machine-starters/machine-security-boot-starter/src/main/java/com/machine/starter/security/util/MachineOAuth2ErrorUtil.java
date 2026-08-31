package com.machine.starter.security.util;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * OAuth2 错误码 → 项目标准中文错误信息 映射工具
 */
public final class MachineOAuth2ErrorUtil {

    private static final String DEFAULT_ERROR_MESSAGE = "OAuth2认证失败";

    private static final Map<String, String> ERROR_MESSAGE_MAP = Map.ofEntries(
            Map.entry(OAuth2ErrorCodes.INVALID_CLIENT, "客户端认证失败，客户端ID或密钥不正确"),
            Map.entry(OAuth2ErrorCodes.INVALID_GRANT, "授权码或刷新令牌无效或已过期"),
            Map.entry(OAuth2ErrorCodes.INVALID_REQUEST, "请求参数无效"),
            Map.entry(OAuth2ErrorCodes.INVALID_SCOPE, "请求的授权范围无效"),
            Map.entry(OAuth2ErrorCodes.INSUFFICIENT_SCOPE, "访问令牌的授权范围不足"),
            Map.entry(OAuth2ErrorCodes.INVALID_REDIRECT_URI, "重定向地址无效"),
            Map.entry(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, "客户端无权使用该授权方式"),
            Map.entry(OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE, "不支持的授权类型"),
            Map.entry(OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, "不支持的响应类型"),
            Map.entry(OAuth2ErrorCodes.ACCESS_DENIED, "用户拒绝授权"),
            Map.entry(OAuth2ErrorCodes.SERVER_ERROR, "授权服务器内部错误"),
            Map.entry(OAuth2ErrorCodes.TEMPORARILY_UNAVAILABLE, "授权服务器暂时不可用，请稍后重试"),
            Map.entry(OAuth2ErrorCodes.INVALID_TOKEN, "访问令牌无效或已过期")
    );

    private MachineOAuth2ErrorUtil() {
    }

    /**
     * 根据 OAuth2 错误码解析项目标准中文错误信息
     */
    public static String resolveMessage(OAuth2Error error) {
        if (error == null) {
            return DEFAULT_ERROR_MESSAGE;
        }
        String message = ERROR_MESSAGE_MAP.get(error.getErrorCode());
        if (StringUtils.hasText(message)) {
            return message;
        }
        return StringUtils.hasText(error.getDescription())
                ? error.getDescription()
                : DEFAULT_ERROR_MESSAGE;
    }
}
