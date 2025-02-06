package com.machine.starter.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.machine.client.iam.auth.IIamOauth2AuthorizationClient;
import com.machine.client.iam.auth.IIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.starter.security.SecurityProperties;
import com.machine.starter.security.config.db.CustomerOAuth2AuthorizationConsentService;
import com.machine.starter.security.config.db.CustomerOAuth2AuthorizationService;
import com.machine.starter.security.config.db.CustomerRegisteredClientRepository;
import com.machine.starter.security.config.token.AppJwtCustomizer;
import com.machine.starter.security.config.token.CustomerKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class AuthServerSecurityConfig {

    @Autowired
    private CustomerKeyService customerKeyService;

    @Autowired
    private IIamOauth2RegisteredClientClient registeredClientClient;

    @Autowired
    private IIamOauth2AuthorizationClient oauth2AuthorizationClient;

    @Autowired
    private IIamOauth2AuthorizationConsentClient authorizationConsentClient;

    /**
     * 注册客户端应用的保存方式
     */
    @Bean
    public CustomerRegisteredClientRepository customerRegisteredClientRepository() {
        return new CustomerRegisteredClientRepository(registeredClientClient);
    }

    /**
     * 令牌的发放记录
     */
    @Bean
    public CustomerOAuth2AuthorizationService customerOAuth2AuthorizationService() {
        return new CustomerOAuth2AuthorizationService(oauth2AuthorizationClient);
    }

    /**
     * 资源拥有者授权确认操作保存到数据库
     */
    @Bean
    public CustomerOAuth2AuthorizationConsentService customerOAuth2AuthorizationConsentService() {
        return new CustomerOAuth2AuthorizationConsentService(authorizationConsentClient);
    }


    /**
     * 授权服务器配置
     * @return
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
//                .authorizationEndpoint("/iam/oauth2/authServer/authorize")
//                .deviceAuthorizationEndpoint("/iam/oauth2/authServer/device_authorization")
//                .deviceVerificationEndpoint("/iam/oauth2/authServer/device_verification")
                .tokenEndpoint("/iam/oauth2/authServer/token")
//                .tokenIntrospectionEndpoint("/iam/oauth2/authServer/introspect")
//                .tokenRevocationEndpoint("/iam/oauth2/authServer/revoke")
//                .jwkSetEndpoint("/iam/oauth2/authServer/jwks")
//                .oidcLogoutEndpoint("/iam/oauth2/authServer/connect/logout")
//                .oidcUserInfoEndpoint("/iam/oauth2/authServer/userinfo")
//                .oidcClientRegistrationEndpoint("/iam/oauth2/authServer/connect/register")
                .build();
    }



    /**
     * jwtEncoder
     */
    @Bean
    @Order(1)
    public JwtEncoder jwtEncoder() {
        JWKSource<SecurityContext> jwkSource = jwkSource();
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * jwt解码器
     */
    @Bean
    @Order(1)
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(customerKeyService.getTokenPublicKey()).build();
    }

    /**
     * jwk来源
     */
    private JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = new RSAKey.Builder(customerKeyService.getTokenPublicKey())
                .privateKey(customerKeyService.getTokenPrivateKey())
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * 身份验证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * token生成器
     */
    @Bean
    public OAuth2TokenGenerator<?> tokenGenerator() {
        JWKSource<SecurityContext> jwkSource = jwkSource();
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(new AppJwtCustomizer());
        return new DelegatingOAuth2TokenGenerator(jwtGenerator);
    }

}