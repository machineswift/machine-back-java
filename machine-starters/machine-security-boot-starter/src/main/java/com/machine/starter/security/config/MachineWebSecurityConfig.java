package com.machine.starter.security.config;

import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.service.MachineUserDetailsService;
import com.machine.starter.security.SecurityProperties;
import com.machine.starter.security.web.AppContextClearFilter;
import com.machine.starter.security.authorization.fallback.FallbackRequestDenyFilter;
import com.machine.starter.security.handler.*;
import com.machine.starter.security.authentication.sms.SmsAuthenticationFilter;
import com.machine.starter.security.authentication.sms.SmsAuthenticationProvider;
import com.machine.starter.security.authentication.username.UsernameAuthenticationFilter;
import com.machine.starter.security.authentication.username.UsernameAuthenticationProvider;
import com.machine.starter.security.authorization.openapi.OpenApiAuthenticationFilter;
import com.machine.starter.security.authorization.internal.InternalJwtAuthenticationFilter;
import com.machine.starter.security.util.MachineJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, proxyTargetClass = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class MachineWebSecurityConfig {

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MachineLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private MachineUnAccessDeniedHandler unAccessDeniedHandler;

    @Autowired
    private MachineAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private MachineUserDetailsService userDetailsService;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CaffeineCacheRegisteredClient caffeineCacheRegisteredClient;

    /**
     * 不鉴权的api
     */
    @Bean
    @Order(20)
    public SecurityFilterChain publicApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher(
                        "/iam/biam/authentication/internal/access_token",
                        "/iam/biam/authentication/internal/picture_captcha",
                        "/iam/biam/authentication/internal/sms_captcha_phone_login",
                        "/iam/biam/authentication/internal/sms_captcha_forget_password",
                        "/iam/biam/authentication/internal/change_password_sms_captcha",

                        "/iam/biam/authentication/thirdParty/render/**",
                        "/iam/biam/authentication/thirdParty/callback/**",

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
    @Order(30)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);

        http.securityMatcher("/iam/biam/authentication/internal/login/*")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        MachineLoginSuccessHandler customerLoginSuccessHandler = applicationContext
                .getBean(MachineLoginSuccessHandler.class);
        MachineLoginFailureHandler customerLoginFailureHandler = applicationContext
                .getBean(MachineLoginFailureHandler.class);

        // 登录方式:用户名、密码登录
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST,
                        "/iam/biam/authentication/internal/login/username"),
                new ProviderManager(List
                        .of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                customerLoginSuccessHandler,
                customerLoginFailureHandler);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 登录方式:手机哈、验证码登录
        SmsAuthenticationFilter smsLoginFilter = new SmsAuthenticationFilter(
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST,
                        "/iam/biam/authentication/internal/login/phone_captcha"),
                new ProviderManager(
                        List.of(applicationContext.getBean(SmsAuthenticationProvider.class))),
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
    public SecurityFilterChain internalApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher("/iam/dictionary/**","/iam/biam/**", "/admin/**", "/partner/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        http.addFilterBefore(new InternalJwtAuthenticationFilter(
                        machineJwtUtil, userDetailsService, customerRedisCommands, authenticationEntryPoint),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 第三方应用鉴权
     */
    @Bean
    @Order(50)
    public SecurityFilterChain openApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http
                .securityMatcher("/openapi/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        OpenApiAuthenticationFilter openApiFilter = new OpenApiAuthenticationFilter(jwtDecoder,
                caffeineCacheRegisteredClient);
        http.addFilterBefore(openApiFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 默认鉴权
     */
    @Bean
    @Order(80)
    public SecurityFilterChain fallbackApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSecuritySetting(http);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        // jwt token 过滤
        http.addFilterBefore(new FallbackRequestDenyFilter(unAccessDeniedHandler),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public HttpFirewall firewall() {
        return new StrictHttpFirewall();
    }

    private void commonHttpSecuritySetting(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(cache -> cache
                        .requestCache(new NullRequestCache()))
                .anonymous(AbstractHttpConfigurer::disable);

        http
                .logout(logout -> logout.logoutUrl("/iam/biam/authentication/internal/logout")
                        .logoutSuccessHandler(logoutSuccessHandler))
                .exceptionHandling(exceptionHandle -> exceptionHandle
                        // 认证异常
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 鉴权异常
                        .accessDeniedHandler(unAccessDeniedHandler));

        http.addFilterBefore(new AppContextClearFilter(), SecurityContextHolderFilter.class);
    }

}