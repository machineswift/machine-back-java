package com.machine.starter.security.service.convert;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;

public class OAuth2Dto2AuthorizationConverter {

    public static OAuth2Authorization toEntity(BIamOAuth2AuthorizationDto authorizationDto,
                                               RegisteredClient registeredClient) {

        if (authorizationDto == null) {
            return null;
        }

        // 1. 创建 Builder
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient);

        // 2. 设置基础信息
        builder.id(authorizationDto.getId())
                .principalName(authorizationDto.getPrincipalName())
                .authorizationGrantType(new AuthorizationGrantType(authorizationDto.getAuthorizationGrantType()));

        // 3. 设置授权范围
        if (StringUtils.hasText(authorizationDto.getAuthorizedScopes())) {
            Set<String> scopes = new HashSet<>(JSONUtil.toList(authorizationDto.getAuthorizedScopes(), String.class));
            builder.authorizedScopes(scopes);
        }

        // 4. 设置属性
        @SuppressWarnings("unchecked")
        Map<String, Object> attributes = JSONUtil.toBean(authorizationDto.getAttributes(), Map.class);
        if (!attributes.isEmpty()) {
            builder.attributes(attrs -> attrs.putAll(attributes));
        }

        // 5. 设置 state (存储在 attributes 中)
        if (StringUtils.hasText(authorizationDto.getState())) {
            builder.attribute(OAuth2ParameterNames.STATE, authorizationDto.getState());
        }

        // 6. 设置授权码
        setAuthorizationCode(builder, authorizationDto);

        // 7. 设置访问令牌
        setAccessToken(builder, authorizationDto);

        // 8. 设置 OIDC ID Token
        setOidcIdToken(builder, authorizationDto);

        // 9. 设置刷新令牌
        setRefreshToken(builder, authorizationDto);

        // 10. 设置用户码
        setUserCode(builder, authorizationDto);

        // 11. 设置设备码
        setDeviceCode(builder, authorizationDto);

        // 12. 构建并返回
        return builder.build();
    }

    /**
     * 设置授权码
     */
    private static void setAuthorizationCode(OAuth2Authorization.Builder builder,
                                             BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getAuthorizationCodeValue())) {
            return;
        }

        OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                authorizationDto.getAuthorizationCodeValue(),
                toInstant(authorizationDto.getAuthorizationCodeIssuedAt()),
                toInstant(authorizationDto.getAuthorizationCodeExpiresAt())
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getAuthorizationCodeMetadata(), Map.class);
        builder.token(authorizationCode, meta -> meta.putAll(metadata));
    }

    /**
     * 设置访问令牌
     */
    private static void setAccessToken(OAuth2Authorization.Builder builder,
                                       BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getAccessTokenValue())) {
            return;
        }

        // 解析范围
        Set<String> scopes = StringUtils.hasText(authorizationDto.getAccessTokenScopes())
                ? StringUtils.commaDelimitedListToSet(authorizationDto.getAccessTokenScopes())
                : Collections.emptySet();

        // 获取 TokenType
        OAuth2AccessToken.TokenType tokenType = getTokenType(authorizationDto.getAccessTokenType());

        // 创建 AccessToken
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                tokenType,
                authorizationDto.getAccessTokenValue(),
                toInstant(authorizationDto.getAccessTokenIssuedAt()),
                toInstant(authorizationDto.getAccessTokenExpiresAt()),
                scopes
        );

        // 设置 metadata
        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getAccessTokenMetadata(), Map.class);

        builder.token(accessToken, meta -> meta.putAll(metadata));
    }

    /**
     * 设置 OIDC ID Token
     */
    private static void setOidcIdToken(OAuth2Authorization.Builder builder,
                                       BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getOidcIdTokenValue())) {
            return;
        }

        // 解析 metadata
        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getOidcIdTokenMetadata(), Map.class);

        // 提取 claims (从 metadata 的 CLAIMS_METADATA_NAME 中获取)
        Map<String, Object> claims = new HashMap<>();
        if (metadata.containsKey(OAuth2Authorization.Token.CLAIMS_METADATA_NAME)) {
            Object claimsObj = metadata.get(OAuth2Authorization.Token.CLAIMS_METADATA_NAME);
            if (claimsObj instanceof Map) {
                claims = (Map<String, Object>) claimsObj;
            }
        }

        // 创建 OidcIdToken
        OidcIdToken oidcIdToken = new OidcIdToken(
                authorizationDto.getOidcIdTokenValue(),
                toInstant(authorizationDto.getOidcIdTokenIssuedAt()),
                toInstant(authorizationDto.getOidcIdTokenExpiresAt()),
                claims
        );

        builder.token(oidcIdToken, meta -> meta.putAll(metadata));
    }

    /**
     * 设置刷新令牌
     */
    private static void setRefreshToken(OAuth2Authorization.Builder builder,
                                        BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getRefreshTokenValue())) {
            return;
        }

        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                authorizationDto.getRefreshTokenValue(),
                toInstant(authorizationDto.getRefreshTokenIssuedAt()),
                toInstant(authorizationDto.getRefreshTokenExpiresAt())
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getRefreshTokenMetadata(), Map.class);
        builder.token(refreshToken, meta -> meta.putAll(metadata));
    }

    /**
     * 设置用户码
     */
    private static void setUserCode(OAuth2Authorization.Builder builder,
                                    BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getUserCodeValue())) {
            return;
        }

        OAuth2UserCode userCode = new OAuth2UserCode(
                authorizationDto.getUserCodeValue(),
                toInstant(authorizationDto.getUserCodeIssuedAt()),
                toInstant(authorizationDto.getUserCodeExpiresAt())
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getUserCodeMetadata(), Map.class);
        builder.token(userCode, meta -> meta.putAll(metadata));
    }

    /**
     * 设置设备码
     */
    private static void setDeviceCode(OAuth2Authorization.Builder builder,
                                      BIamOAuth2AuthorizationDto authorizationDto) {
        if (!StringUtils.hasText(authorizationDto.getDeviceCodeValue())) {
            return;
        }

        OAuth2DeviceCode deviceCode = new OAuth2DeviceCode(
                authorizationDto.getDeviceCodeValue(),
                toInstant(authorizationDto.getDeviceCodeIssuedAt()),
                toInstant(authorizationDto.getDeviceCodeExpiresAt())
        );

        @SuppressWarnings("unchecked")
        Map<String, Object> metadata = JSONUtil.toBean(authorizationDto.getDeviceCodeMetadata(), Map.class);
        builder.token(deviceCode, meta -> meta.putAll(metadata));
    }

    // ==================== 工具方法 ====================

    /**
     * 时间戳(毫秒) -> Instant
     */
    private static Instant toInstant(Long timestamp) {
        return timestamp != null ? Instant.ofEpochMilli(timestamp) : null;
    }

    /**
     * 获取 TokenType
     */
    private static OAuth2AccessToken.TokenType getTokenType(String tokenType) {
        if (!StringUtils.hasText(tokenType)) {
            return OAuth2AccessToken.TokenType.BEARER;
        }

        if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(tokenType)) {
            return OAuth2AccessToken.TokenType.BEARER;
        } else if (OAuth2AccessToken.TokenType.DPOP.getValue().equalsIgnoreCase(tokenType)) {
            return OAuth2AccessToken.TokenType.DPOP;
        }

        // 默认返回 BEARER
        return OAuth2AccessToken.TokenType.BEARER;
    }

}
