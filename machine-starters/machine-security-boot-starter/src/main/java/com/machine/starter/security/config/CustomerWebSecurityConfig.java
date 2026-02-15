package com.machine.starter.security.config;

import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.starter.redis.cache.LocalCacheRegisteredClient;
import com.machine.starter.redis.cache.LocalCacheSystemConfig;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.CustomerUserDetailsService;
import com.machine.starter.security.SecurityProperties;
import com.machine.starter.security.filter.CustomerContextHolderFilter;
import com.machine.starter.security.handler.*;
import com.machine.starter.security.login.sms.SmsAuthenticationFilter;
import com.machine.starter.security.login.sms.SmsAuthenticationProvider;
import com.machine.starter.security.login.username.UserNameAuthenticationFilter;
import com.machine.starter.security.login.username.UsernameAuthenticationProvider;
import com.machine.starter.security.resource.defbult.DefaultJwtAuthenticationFilter;
import com.machine.starter.security.resource.open.OpenApiAuthenticationFilter;
import com.machine.starter.security.resource.self.SelfJwtAuthenticationFilter;
import com.machine.starter.security.util.MachineJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class CustomerWebSecurityConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CustomerLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private CustomerUnAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomerAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomerUserDetailsService userDetailsService;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private LocalCacheSystemConfig localCacheSystemConfig;

    @Autowired
    private LocalCacheRegisteredClient localCacheRegisteredClient;

    @Autowired
    private IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;


    /**
     * 不鉴权的api
     */
    @Bean
    @Order(10)
    public SecurityFilterChain publicApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher(
                        "/iam/auth/access_token",
                        "/iam/auth/picture_captcha",
                        "/iam/auth/sms_captcha_phone_login",
                        "/iam/auth/sms_captcha_forget_password",
                        "/iam/auth2/render/**",
                        "/iam/auth2/callback/**",
                        "/iam/oauth2/authServer",
                        "/iam/current/change_password_sms_captcha",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/error")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }

    /**
     * 用户登录鉴权
     */
    @Bean
    @Order(20)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);

        http.securityMatcher("/iam/auth/login/*", "/iam/auth2/login/*")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        CustomerLoginSuccessHandler customerLoginSuccessHandler = applicationContext.getBean(CustomerLoginSuccessHandler.class);
        CustomerLoginFailureHandler customerLoginFailureHandler = applicationContext.getBean(CustomerLoginFailureHandler.class);

        //登录方式:用户名、密码登录
        UserNameAuthenticationFilter usernameLoginFilter = new UserNameAuthenticationFilter(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/iam/auth/login/username"),
                new ProviderManager(List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                customerLoginSuccessHandler,
                customerLoginFailureHandler);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        //登录方式:手机哈、验证码登录
        SmsAuthenticationFilter smsLoginFilter = new SmsAuthenticationFilter(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/iam/auth/login/phone_captcha"),
                new ProviderManager(List.of(applicationContext.getBean(SmsAuthenticationProvider.class))),
                customerLoginSuccessHandler,
                customerLoginFailureHandler);
        http.addFilterBefore(smsLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 自己服务鉴权
     */
    @Bean
    @Order(40)
    public SecurityFilterChain selfApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher("/iam/**", "/manage/**", "/super/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        http.addFilterBefore(new SelfJwtAuthenticationFilter(machineJwtUtil, userDetailsService, customerRedisCommands),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 第三方应用鉴权
     */
    @Bean
    @Order(50)
    public SecurityFilterChain thirdApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher("/openapi/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        OpenApiAuthenticationFilter openApiFilter = new OpenApiAuthenticationFilter(activeProfile,
                jwtDecoder, localCacheSystemConfig, localCacheRegisteredClient, oauth2RegisteredClientClient);
        http.addFilterBefore(openApiFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 默认鉴权
     */
    @Bean
    @Order(80)
    public SecurityFilterChain defaultApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        // jwt token 过滤
        http.addFilterBefore(new DefaultJwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public HttpFirewall firewall() {
        return new StrictHttpFirewall();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String encodingId = "scrypt@SpringSecurity_v5_8";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        //不推荐以下加密方式
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
        encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
        encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    private void commonHttpSecuritySetting(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(cache -> cache
                        .requestCache(new NullRequestCache())
                )
                .anonymous(AbstractHttpConfigurer::disable);

        http
                .logout(logout ->
                        logout.logoutUrl("/iam/auth/logout").logoutSuccessHandler(logoutSuccessHandler)
                )
                .exceptionHandling(exceptionHandle ->
                        exceptionHandle
                                //认证异常
                                .authenticationEntryPoint(authenticationEntryPoint)
                                //鉴权异常
                                .accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(new CustomerContextHolderFilter(), SecurityContextHolderFilter.class);
    }

}