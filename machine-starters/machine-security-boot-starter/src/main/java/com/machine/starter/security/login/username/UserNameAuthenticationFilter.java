package com.machine.starter.security.login.username;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class UserNameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UserNameAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
                                        AuthenticationManager authenticationManager,
                                        AuthenticationSuccessHandler authenticationSuccessHandler,
                                        AuthenticationFailureHandler authenticationFailureHandler) {
        super(pathRequestMatcher);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        // 提取请求数据
        UsernameAuthenticationToken usernameRequest = JSONUtil.toBean(IoUtil.read(request.getInputStream(),
                Charset.defaultCharset()), UsernameAuthenticationToken.class);

        log.info("用户名密码登录，userName={} captcha={} userKey={}",
                usernameRequest.getUserName(), usernameRequest.getCaptcha(), usernameRequest.getUserKey());

        // 封装成Spring Security需要的对象
        UsernameAuthenticationToken authentication = new UsernameAuthenticationToken();
        authentication.setUserName(usernameRequest.getUserName());
        authentication.setPassword(usernameRequest.getPassword());
        authentication.setCaptcha(usernameRequest.getCaptcha());
        authentication.setUserKey(usernameRequest.getUserKey());
        authentication.setAuthenticated(false);

        return getAuthenticationManager().authenticate(authentication);
    }
}