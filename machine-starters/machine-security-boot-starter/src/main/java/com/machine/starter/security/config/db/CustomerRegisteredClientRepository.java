package com.machine.starter.security.config.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.auth.dto.IamOAuth2RegisteredClientDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.*;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

public class CustomerRegisteredClientRepository implements RegisteredClientRepository {

    private final  IIamOauth2RegisteredClientClient registeredClientClient;

    public CustomerRegisteredClientRepository(IIamOauth2RegisteredClientClient registeredClientClient) {
        this.registeredClientClient = registeredClientClient;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        RegisteredClient existingRegisteredClient = findById(registeredClient.getId());
        if (existingRegisteredClient != null) {
            updateRegisteredClient(registeredClient);
        } else {
            insertRegisteredClient(registeredClient);
        }

    }

    public void insertRegisteredClient(RegisteredClient registeredClient) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDto dto = new IamOAuth2RegisteredClientDto();
        dto.setClientId(registeredClient.getClientId());
        dto.setId(registeredClient.getId());
        dto.setClientSecret(registeredClient.getClientSecret());
        dto.setClientIdIssuedAt(System.currentTimeMillis());
        dto.setClientSecretExpiresAt(null);
        dto.setClientName(registeredClient.getClientName());
        dto.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).toList().get(0));
        dto.setAuthorizationGrantTypes(String.join(",",registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).toList()));
        dto.setRedirectUris(String.join(",",registeredClient.getRedirectUris().stream().toList()));
        dto.setScopes(String.join(",", registeredClient.getScopes()));
        dto.setClientSettings(JSONUtil.toJsonStr(registeredClient.getClientSettings()));
        dto.setTokenSettings(JSONUtil.toJsonStr(registeredClient.getTokenSettings()));
        registeredClientClient.save(dto);
    }

    @Override
    public RegisteredClient findById(String id) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDetailOutputDto dto = registeredClientClient.getById(id);
        if(Objects.isNull(dto)){
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(dto), RegisteredClient.class);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDetailOutputDto dto = registeredClientClient.getByClientId(clientId);
        if(Objects.isNull(dto)){
            return null;
        }
        String tokenSettings = dto.getTokenSettings();
        Map<String, Object> tokenSettingsMap = JSONUtil.toBean(tokenSettings,Map.class);
                Map<String, Object> settings = JSONUtil.toBean(JSONUtil.toJsonStr(tokenSettingsMap.get("settings")),Map.class);
        TokenSettings tokenSettingsObj = TokenSettings.builder()
                .accessTokenTimeToLive(Duration.parse(settings.get("settings.token.access-token-time-to-live").toString()))
                .refreshTokenTimeToLive(Duration.parse(settings.get("settings.token.refresh-token-time-to-live").toString()))
                .reuseRefreshTokens((Boolean) settings.get("settings.token.reuse-refresh-tokens"))
                .build();

         String clientSettings = dto.getClientSettings();
        Map<String, Object> clientSettingsMap = JSONUtil.toBean(clientSettings,Map.class);
                        Map<String, Object> clientSet = JSONUtil.toBean(JSONUtil.toJsonStr(clientSettingsMap.get("settings")),Map.class);

        ClientSettings clientSettingsObj = ClientSettings.builder()
                .requireProofKey((Boolean) clientSet.get("settings.client.require-proof-key"))
                .requireAuthorizationConsent((Boolean) clientSet.get("settings.client.require-authorization-consent"))
                .build();

         RegisteredClient registeredClient = RegisteredClient.withId(dto.getId())
                .clientId(dto.getClientId())
                .clientName(dto.getClientName())
                .clientSecret(dto.getClientSecret())
                .clientAuthenticationMethods(authMethods -> {
                          authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

                })
                .authorizationGrantTypes(grantTypes -> {
                    String[] split = dto.getAuthorizationGrantTypes().split(",");
                    Set<String> list = new HashSet<>(Arrays.asList(split));
                    list.stream().forEach(grantType -> grantTypes.add(new AuthorizationGrantType(grantType)));
                })
                .redirectUris(redirectUris -> {
                     String[] split = dto.getRedirectUris().split(",");
                    Set<String> redirectUrisSet = new HashSet<>(Arrays.asList(split));
                    redirectUrisSet.forEach(redirectUri -> redirectUris.add(redirectUri));
                })
                .scopes(scopes -> {
                     String[] split = dto.getScopes().split(",");
                    Set<String> scopesSet = new HashSet<>( Arrays.asList(split));
                    scopesSet.forEach(scope -> scopes.add(scope));
                })
                .clientSettings(clientSettingsObj)
                .tokenSettings(tokenSettingsObj)
                .build();

        return registeredClient;
    }

    private void updateRegisteredClient(RegisteredClient registeredClient) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2RegisteredClientDto dto = new IamOAuth2RegisteredClientDto();
        dto.setClientId(registeredClient.getClientId());
        dto.setId(registeredClient.getId());
        dto.setClientSecret(registeredClient.getClientSecret());
        dto.setClientIdIssuedAt(System.currentTimeMillis());
        dto.setClientSecretExpiresAt(null);
        dto.setClientName(registeredClient.getClientName());
        dto.setClientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::getValue).toList().get(0));
        dto.setAuthorizationGrantTypes(String.join(",",registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).toList()));
        dto.setRedirectUris(String.join(",",registeredClient.getRedirectUris().stream().toList()));
        dto.setScopes(String.join(",", registeredClient.getScopes()));
        dto.setClientSettings(JSONUtil.toJsonStr(registeredClient.getClientSettings()));
        dto.setTokenSettings(JSONUtil.toJsonStr(registeredClient.getTokenSettings()));
        registeredClientClient.update(dto);
    }
}
