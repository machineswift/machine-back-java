package com.machine.starter.security.service.convert;

import com.machine.sdk.base.envm.biam.identity.BIamAuthorizationGrantTypeEnum;
import com.machine.sdk.base.model.dto.biam.identity.BIamAuth2RegisteredClientSettingDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamAuth2RegisteredTokenSettingDto;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.Instant;

public class OAuth2Dto2RegisteredClientConverter {

    public static RegisteredClient convert2Client(BIamOAuth2RegisteredClientDto clientDto) {
        RegisteredClient.Builder builder = RegisteredClient.withId(clientDto.getId())
                .clientId(clientDto.getClientId())
                .clientSecret(clientDto.getClientSecret())
                .clientName(clientDto.getClientName())
                // 毫秒 → Instant
                .clientIdIssuedAt(Instant.ofEpochMilli(clientDto.getClientIdIssuedAt()));

        // 密钥过期时间（可能为 null）
        if (clientDto.getClientSecretExpiresAt() != null) {
            builder.clientSecretExpiresAt(Instant.ofEpochMilli(clientDto.getClientSecretExpiresAt()));
        }

        // 认证方法
        builder.clientAuthenticationMethods(methods -> {
            clientDto.getClientAuthenticationMethods()
                    .forEach(m -> methods.add(new ClientAuthenticationMethod(m.trim())));
        });

        // 授权类型
        builder.authorizationGrantTypes(types -> {
            clientDto.getAuthorizationGrantTypes()
                    .forEach(g -> types.add(new AuthorizationGrantType(g.trim())));
        });

        // 重定向 URI
        builder.redirectUris(uris -> uris.addAll(clientDto.getRedirectUris()));

        // 登出后重定向 URI
        builder.postLogoutRedirectUris(uris -> uris.addAll(clientDto.getPostLogoutRedirectUris()));

        // 客户端作用域
        if (BIamAuthorizationGrantTypeEnum.CLIENT_CREDENTIALS == clientDto.getAuthorizationGrantType()) {
            builder.scopes(s -> s.add("CLIENT_CREDENTIALS"));
        } else if (BIamAuthorizationGrantTypeEnum.AUTHORIZATION_CODE == clientDto.getAuthorizationGrantType()) {
            builder.scopes(s -> s.addAll(clientDto.getScopes()));
        }

        // 客户端设置
        builder.clientSettings(buildClientSettings(clientDto.getClientSettings()));

        // 令牌设置
        builder.tokenSettings(buildTokenSettings(clientDto.getTokenSettings()));
        return builder.build();
    }

    private static ClientSettings buildClientSettings(BIamAuth2RegisteredClientSettingDto settingDto) {
        ClientSettings.Builder builder = ClientSettings.builder();

        if (settingDto != null) {
            // 强制PKCE
            if (settingDto.getRequireProofKey() != null) {
                builder.requireProofKey(settingDto.getRequireProofKey());
            }

            // 需要用户确
            if (settingDto.getRequireAuthorizationConsent() != null) {
                builder.requireAuthorizationConsent(settingDto.getRequireAuthorizationConsent());
            }
        }
        return builder.build();
    }

    private static TokenSettings buildTokenSettings(BIamAuth2RegisteredTokenSettingDto settingDto) {
        TokenSettings.Builder builder = TokenSettings.builder();

        if (settingDto != null) {
            // 是否重用刷新令牌
            if (settingDto.getReuseRefreshTokens() != null) {
                builder.reuseRefreshTokens(settingDto.getReuseRefreshTokens());
            }

            // 是否将访问令牌绑定到客户端的X.509证书
            if (settingDto.getX509CertificateBoundAccessTokens() != null) {
                builder.x509CertificateBoundAccessTokens(settingDto.getX509CertificateBoundAccessTokens());
            }

            // idTokenSignatureAlgorithm
            if (settingDto.getIdTokenSignatureAlgorithm() != null) {
                builder.idTokenSignatureAlgorithm(SignatureAlgorithm.from(settingDto.getIdTokenSignatureAlgorithm()));
            }

            // accessTokenTimeToLive（小时 → 秒）
            if (settingDto.getAccessTokenTimeToLive() != null) {
                builder.accessTokenTimeToLive(Duration.ofHours(settingDto.getAccessTokenTimeToLive()));
            }

            // refreshTokenTimeToLive（天 → 秒）
            if (settingDto.getRefreshTokenTimeToLive() != null) {
                builder.refreshTokenTimeToLive(Duration.ofDays(settingDto.getRefreshTokenTimeToLive()));
            }

            // accessTokenFormat
            if (settingDto.getAccessTokenFormat() != null) {
                builder.accessTokenFormat(new OAuth2TokenFormat(settingDto.getAccessTokenFormat()));
            }
        }
        return builder.build();
    }

}
