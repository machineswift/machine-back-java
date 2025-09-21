package com.machine.app.iam.auth.business.impl;

import com.machine.app.iam.auth.business.IIamAuthServerBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.starter.security.config.db.CustomerRegisteredClientRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
@RefreshScope
public class IamAuthServerBusinessImpl implements IIamAuthServerBusiness {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomerRegisteredClientRepository clientRepository;

    @Override
    public String createClient(IamAuthCreateClientRequestVo request) {
        RegisteredClient clientInfo = clientRepository.findByClientId(request.getClientId());
        if (null != clientInfo) {
            throw new IamBusinessException("iam.authServer.service.createClient.existClientId", "客户已经存在");
        }
        String id = UUID.randomUUID().toString().replace("-", "");
        String secret = passwordEncoder.encode(request.getPassword());
        clientInfo = RegisteredClient.withId(id)
                .clientId(request.getClientId())
                .clientName(request.getClientName())
                .clientSecret(secret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scopes(scopes -> scopes.addAll(request.getScopes()))
                .redirectUris(uris -> uris.addAll(request.getRedirectUrls()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .deviceCodeTimeToLive(Duration.ofMinutes(10))
                        .accessTokenTimeToLive(Duration.ofHours(2))
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .build())
                .build();
        clientRepository.insertRegisteredClient(clientInfo);

        // clientRepository.updateRegisteredClient(clientInfo);
        return id;
    }
}
