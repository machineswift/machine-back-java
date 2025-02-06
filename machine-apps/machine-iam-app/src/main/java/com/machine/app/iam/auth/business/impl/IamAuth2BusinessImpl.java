package com.machine.app.iam.auth.business.impl;

import com.machine.app.iam.auth.business.IIamAuth2Business;
import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;
import com.machine.sdk.common.exception.iam.IamAuth2BusinessException;
import com.machine.starter.security.config.db.CustomerRegisteredClientRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
@RefreshScope
public class IamAuth2BusinessImpl implements IIamAuth2Business {

    @Value("${auth2.gitee.clientId:XXX}")
    private String giteeClientId;

    @Value("${auth2.gitee.clientSecret:XXX}")
    private String giteeClientSecret;

    @Value("${auth2.gitee.redirectUri:XXX}")
    private String giteeRedirectUri;

    @Value("${auth2.feiShu.clientId:XXX}")
    private String feishuClientId;

    @Value("${auth2.feiShu.clientSecret:XXX}")
    private String feishuClientSecret;

    @Value("${auth2.feiShu.redirectUri:XXX}")
    private String feishuRedirectUri;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private CustomerRegisteredClientRepository clientRepository;

    @Override
    public void gitee(HttpServletResponse response) {
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId(giteeClientId)
                .clientSecret(giteeClientSecret)
                .redirectUri(giteeRedirectUri)
                .build());

        String redirectUrl = authRequest.authorize(AuthStateUtils.createState());
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("auth2.0 重定向报错", e);
            throw new IamAuth2BusinessException("iam.auth2.gitee.render", e.getMessage(), e);
        }
    }

    @Override
    public String createClient(IamAuthCreateClientRequestVo request) {
        RegisteredClient clientInfo = clientRepository.findByClientId(request.getClientId());
        if (null != clientInfo) {
            throw new IamAuth2BusinessException("iam.auth2.createClient.existClientId", "客户已经存在");
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
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
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

    @Override
    public void feishu(HttpServletResponse response) {
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
                .clientId(feishuClientId)
                .clientSecret(feishuClientSecret)
                .redirectUri(feishuRedirectUri)
                .build());

        String redirectUrl = authRequest.authorize(AuthStateUtils.createState());
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("auth2.0 重定向报错", e);
            throw new IamAuth2BusinessException("iam.auth2.feiShu.render", e.getMessage(), e);
        }
    }
}
