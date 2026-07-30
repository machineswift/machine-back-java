package com.machine.starter.security.config.db;

import com.machine.client.iam.identity.IIamOauth2RegisteredClientClient;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.exception.iam.IamBusinessException;
import com.machine.sdk.base.model.dto.iam.identity.IamAuth2RegisteredClientSettingDto;
import com.machine.sdk.base.model.dto.iam.identity.IamAuth2RegisteredTokenSettingDto;
import com.machine.sdk.base.model.dto.iam.identity.IamOAuth2RegisteredClientDto;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

public class CustomerRegisteredClientRepository implements RegisteredClientRepository {

    private final IIamOauth2RegisteredClientClient registeredClientClient;

    public CustomerRegisteredClientRepository(IIamOauth2RegisteredClientClient registeredClientClient) {
        this.registeredClientClient = registeredClientClient;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        throw new IamBusinessException("iam.identity.repository.save.notSupportedAutoCreate", "认证中心客户端不支持自动创建");
    }

    @Override
    public RegisteredClient findById(String id) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDto clientDto = registeredClientClient.getById(id);
        if (Objects.isNull(clientDto)) {
            return null;
        }
        return convert2Client(clientDto);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDto clientDto = registeredClientClient.getByClientId(clientId);
        if (Objects.isNull(clientDto)) {
            return null;
        }
        return convert2Client(clientDto);
    }

    private RegisteredClient convert2Client(IamOAuth2RegisteredClientDto clientDto) {
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
        OAuth2TokenFormat tokenFormat = getTokenFormat(clientDto);
        if (OAuth2TokenFormat.SELF_CONTAINED.equals(tokenFormat)) {
            builder.scopes(s -> s.addAll(clientDto.getScopes()));
        }

        // 客户端设置
        builder.clientSettings(buildClientSettings(clientDto.getClientSettings()));

        // 令牌设置
        builder.tokenSettings(buildTokenSettings(clientDto.getTokenSettings()));
        return builder.build();
    }

    private OAuth2TokenFormat getTokenFormat(IamOAuth2RegisteredClientDto clientDto) {
        if (clientDto.getTokenSettings() != null
                && clientDto.getTokenSettings().getAccessTokenFormat() != null) {
            return clientDto.getTokenSettings().getAccessTokenFormat();
        }
        return OAuth2TokenFormat.REFERENCE;
    }

    private ClientSettings buildClientSettings(IamAuth2RegisteredClientSettingDto settingDto) {
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

    private TokenSettings buildTokenSettings(IamAuth2RegisteredTokenSettingDto settingDto) {
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
                builder.idTokenSignatureAlgorithm(settingDto.getIdTokenSignatureAlgorithm());
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
                builder.accessTokenFormat(settingDto.getAccessTokenFormat());
            }
        }
        return builder.build();
    }

}
