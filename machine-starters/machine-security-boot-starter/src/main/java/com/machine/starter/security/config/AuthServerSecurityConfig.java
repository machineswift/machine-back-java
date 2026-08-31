package com.machine.starter.security.config;

import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import com.machine.client.iam.biam.identity.IBIamOauth2AuthorizationClient;
import com.machine.client.iam.biam.auth.IBIamOauth2AuthorizationConsentClient;
import com.machine.starter.security.SecurityProperties;
import com.machine.starter.security.service.AuthServerUserDetailsService;
import com.machine.starter.security.service.MachineOAuth2AuthorizationConsentService;
import com.machine.starter.security.service.MachineOAuth2AuthorizationService;
import com.machine.starter.security.service.repository.MachineRegisteredClientRepository;
import com.machine.starter.security.handler.oauth2.MachineOAuth2ClientAuthenticationFailureHandler;
import com.machine.starter.security.handler.oauth2.MachineOAuth2TokenEndpointErrorHandler;
import com.machine.starter.security.handler.oauth2.MachineOAuth2TokenEndpointResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.ClientSecretAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class AuthServerSecurityConfig {

    @Autowired
    private CaffeineCacheRegisteredClient cacheRegisteredClient;

    @Autowired
    private IBIamOauth2RegisteredClientClient oauth2RegisteredClient;

    @Autowired
    private IBIamOauth2AuthorizationClient oauth2AuthorizationClient;

    @Autowired
    private IBIamOauth2AuthorizationConsentClient authorizationConsentClient;


    @Bean
    public MachineRegisteredClientRepository customerRegisteredClientRepository() {
        return new MachineRegisteredClientRepository(oauth2RegisteredClient, cacheRegisteredClient);
    }

    /**
     * 令牌的发放记录
     */
    @Bean
    public MachineOAuth2AuthorizationService customerOAuth2AuthorizationService() {
        return new MachineOAuth2AuthorizationService(oauth2AuthorizationClient, cacheRegisteredClient);
    }

    /**
     * 资源拥有者授权确认操作保存到数据库
     */
    @Bean
    public MachineOAuth2AuthorizationConsentService customerOAuth2AuthorizationConsentService() {
        return new MachineOAuth2AuthorizationConsentService(authorizationConsentClient);
    }

    /**
     * 客户端密钥认证 Provider
     */
    @Bean
    public ClientSecretAuthenticationProvider clientSecretAuthenticationProvider(RegisteredClientRepository registeredClientRepository,
                                                                                 OAuth2AuthorizationService oauth2AuthorizationService,
                                                                                 PasswordEncoder passwordEncoder) {
        ClientSecretAuthenticationProvider provider = new ClientSecretAuthenticationProvider(registeredClientRepository,
                oauth2AuthorizationService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * 授权服务器表单登录（授权码模式的资源拥有者 session 认证）
     */
    @Bean
    @Order(5)
    @ConditionalOnProperty(name = "machine.iam.identity.auth2.authorization-server-enabled", havingValue = "true")
    public SecurityFilterChain authServerLoginSecurityFilterChain(HttpSecurity http,
                                                                  AuthServerUserDetailsService authServerUserDetailsService) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher(SecurityConstant.AUTH_SERVER_LOGIN_PAGE, SecurityConstant.AUTH_SERVER_CONSENT_PAGE)
                .authorizeHttpRequests(authorize -> authorize
                        // 登录页 GET 渲染、POST 由 formLogin 的 UsernamePasswordAuthenticationFilter 处理
                        .requestMatchers(SecurityConstant.AUTH_SERVER_LOGIN_PAGE).permitAll()
                        // 授权确认页要求已认证（session）
                        .anyRequest().authenticated())
                .userDetailsService(authServerUserDetailsService)
                .formLogin(form -> form
                        .loginPage(SecurityConstant.AUTH_SERVER_LOGIN_PAGE)
                        .loginProcessingUrl(SecurityConstant.AUTH_SERVER_LOGIN_PAGE)
                        .failureUrl(SecurityConstant.AUTH_SERVER_LOGIN_PAGE + "?error"));
        return http.build();
    }

    /**
     * 授权服务器安全过滤链
     */
    @Bean
    @Order(10)
    @ConditionalOnProperty(name = "machine.iam.identity.auth2.authorization-server-enabled", havingValue = "true")
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      MachineOAuth2ClientAuthenticationFailureHandler clientAuthenticationFailureHandler,
                                                                      MachineOAuth2TokenEndpointResponseHandler tokenEndpointResponseHandler,
                                                                      MachineOAuth2TokenEndpointErrorHandler tokenEndpointErrorHandler,
                                                                      OAuth2TokenGenerator<?> tokenGenerator) {
        // getEndpointsMatcher() 自动覆盖全部授权服务器端点
        // （authorize/token/introspect/revoke/jwks/userinfo/logout/oidc-provider-config
        // 等）
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                // 授权服务器端点为浏览器间接提交（授权确认），按官方方案仅对端点放行 CSRF
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .securityMatcher(endpointsMatcher)
                .with(authorizationServerConfigurer, (authorizationServer) -> authorizationServer
                        .tokenGenerator(tokenGenerator)
                        // 启用 OIDC：提供方发现文档、userinfo、RP-Initiated Logout
                        .oidc(Customizer.withDefaults())
                        // 授权码模式的授权确认页（由 AuthServerConsentController 渲染）
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .consentPage(SecurityConstant.AUTH_SERVER_CONSENT_PAGE))
                        .clientAuthentication(clientAuthentication -> clientAuthentication
                                .errorResponseHandler(clientAuthenticationFailureHandler))
                        .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                                .accessTokenResponseHandler(tokenEndpointResponseHandler)
                                .errorResponseHandler(tokenEndpointErrorHandler)))
                // 资源拥有者未认证时，由授权端点拦截并重定向到表单登录页
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint(SecurityConstant.AUTH_SERVER_LOGIN_PAGE),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
        return http.build();
    }

    /**
     * 授权服务器配置
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                // 授权码模式（OIDC）端点，与 login/consent 页面路径保持一致
                .authorizationEndpoint(SecurityConstant.AUTH_SERVER_AUTHORIZE_ENDPOINT)
                .oidcUserInfoEndpoint(SecurityConstant.AUTH_SERVER_USERINFO_ENDPOINT)
                .oidcLogoutEndpoint(SecurityConstant.AUTH_SERVER_LOGOUT_ENDPOINT)
                // 客户端凭证模式端点（保持不变，兼容既有调用方）
                .tokenEndpoint(SecurityConstant.AUTH_SERVER_TOKEN_ENDPOINT)
                .tokenIntrospectionEndpoint(SecurityConstant.AUTH_SERVER_INTROSPECT_ENDPOINT)
                .tokenRevocationEndpoint(SecurityConstant.AUTH_SERVER_REVOKE_ENDPOINT)
                .jwkSetEndpoint(SecurityConstant.AUTH_SERVER_JWKS_ENDPOINT)
                // 生产环境（尤其是网关后）建议显式配置 issuer：
                // .issuer("${machine.iam.auth2.issuer:}")
                // .deviceAuthorizationEndpoint("/iam/biam/oauth2/authServer/device_authorization")
                // .deviceVerificationEndpoint("/iam/biam/oauth2/authServer/device_verification")
                // .oidcClientRegistrationEndpoint("/iam/biam/oauth2/authServer/connect/register")
                .build();
    }


}