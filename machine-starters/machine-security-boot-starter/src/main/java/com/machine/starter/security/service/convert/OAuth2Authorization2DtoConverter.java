package com.machine.starter.security.service.convert;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Set;

public class OAuth2Authorization2DtoConverter {

    public static BIamOAuth2AuthorizationDto toDto(OAuth2Authorization authorization) {
        if (authorization == null) {
            return null;
        }

        BIamOAuth2AuthorizationDto dto = new BIamOAuth2AuthorizationDto();

        // 1. 基础信息
        dto.setId(authorization.getId());
        dto.setRegisteredClientId(authorization.getRegisteredClientId());
        dto.setPrincipalName(authorization.getPrincipalName());
        dto.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());

        // authorized_scopes: Set -> 逗号分隔字符串
        Set<String> scopes = authorization.getAuthorizedScopes();
        if (!CollectionUtils.isEmpty(scopes)) {
            dto.setAuthorizedScopes(JSONUtil.toJsonStr(scopes));
        }

        // attributes: Map -> JSON字符串 (可使用Jackson序列化)
        dto.setAttributes(JSONUtil.toJsonStr(authorization.getAttributes()));

        // state: 从attributes中获取
        String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
        dto.setState(state);

        // 2. 处理授权码 (Authorization Code)
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authCodeToken =
                authorization.getToken(OAuth2AuthorizationCode.class);
        if (authCodeToken != null) {
            OAuth2AuthorizationCode authCode = authCodeToken.getToken();
            dto.setAuthorizationCodeValue(authCode.getTokenValue());
            dto.setAuthorizationCodeIssuedAt(toEpochMilli(authCode.getIssuedAt()));
            dto.setAuthorizationCodeExpiresAt(toEpochMilli(authCode.getExpiresAt()));
            dto.setAuthorizationCodeMetadata(JSONUtil.toJsonStr(authCodeToken.getMetadata()));
        }

        // 3. 处理访问令牌 (Access Token)
        OAuth2Authorization.Token<OAuth2AccessToken> accessTokenToken =
                authorization.getToken(OAuth2AccessToken.class);
        if (accessTokenToken != null) {
            OAuth2AccessToken accessToken = accessTokenToken.getToken();
            dto.setAccessTokenValue(accessToken.getTokenValue());
            dto.setAccessTokenIssuedAt(toEpochMilli(accessToken.getIssuedAt()));
            dto.setAccessTokenExpiresAt(toEpochMilli(accessToken.getExpiresAt()));
            dto.setAccessTokenMetadata(JSONUtil.toJsonStr(accessTokenToken.getMetadata()));
            dto.setAccessTokenType(accessToken.getTokenType().getValue());

            Set<String> accessScopes = accessToken.getScopes();
            if (!CollectionUtils.isEmpty(accessScopes)) {
                dto.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessScopes, ","));
            }
        }

        // 4. 处理 OIDC ID Token
        OAuth2Authorization.Token<OidcIdToken> oidcToken =
                authorization.getToken(OidcIdToken.class);
        if (oidcToken != null) {
            OidcIdToken idToken = oidcToken.getToken();
            dto.setOidcIdTokenValue(idToken.getTokenValue());
            dto.setOidcIdTokenIssuedAt(toEpochMilli(idToken.getIssuedAt()));
            dto.setOidcIdTokenExpiresAt(toEpochMilli(idToken.getExpiresAt()));
            dto.setOidcIdTokenMetadata(JSONUtil.toJsonStr(oidcToken.getMetadata()));
        }

        // 5. 处理刷新令牌 (Refresh Token)
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken =
                authorization.getRefreshToken();
        if (refreshToken != null) {
            OAuth2RefreshToken token = refreshToken.getToken();
            dto.setRefreshTokenValue(token.getTokenValue());
            dto.setRefreshTokenIssuedAt(toEpochMilli(token.getIssuedAt()));
            dto.setRefreshTokenExpiresAt(toEpochMilli(token.getExpiresAt()));
            dto.setRefreshTokenMetadata(JSONUtil.toJsonStr(refreshToken.getMetadata()));
        }

        // 6. 处理用户码 (User Code)
        OAuth2Authorization.Token<OAuth2UserCode> userCodeToken =
                authorization.getToken(OAuth2UserCode.class);
        if (userCodeToken != null) {
            OAuth2UserCode userCode = userCodeToken.getToken();
            dto.setUserCodeValue(userCode.getTokenValue());
            dto.setUserCodeIssuedAt(toEpochMilli(userCode.getIssuedAt()));
            dto.setUserCodeExpiresAt(toEpochMilli(userCode.getExpiresAt()));
            dto.setUserCodeMetadata(JSONUtil.toJsonStr(userCodeToken.getMetadata()));
        }

        // 7. 处理设备码 (Device Code)
        OAuth2Authorization.Token<OAuth2DeviceCode> deviceCodeToken =
                authorization.getToken(OAuth2DeviceCode.class);
        if (deviceCodeToken != null) {
            OAuth2DeviceCode deviceCode = deviceCodeToken.getToken();
            dto.setDeviceCodeValue(deviceCode.getTokenValue());
            dto.setDeviceCodeIssuedAt(toEpochMilli(deviceCode.getIssuedAt()));
            dto.setDeviceCodeExpiresAt(toEpochMilli(deviceCode.getExpiresAt()));
            dto.setDeviceCodeMetadata(JSONUtil.toJsonStr(deviceCodeToken.getMetadata()));
        }

        return dto;
    }

    private static Long toEpochMilli(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }

}
