package com.machine.starter.security.login.sms;

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
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsAuthenticationFilter(PathPatternRequestMatcher pathRequestMatcher,
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
        SmsAuthenticationToken smsRequest = JSONUtil.toBean(IoUtil.read(request.getInputStream(),
                Charset.defaultCharset()), SmsAuthenticationToken.class);

        log.info("手机号登录，phone={} captcha={}",
                smsRequest.getPhone(), smsRequest.getCaptcha());

        SmsAuthenticationToken authentication = new SmsAuthenticationToken();
        authentication.setPhone(smsRequest.getPhone());
        authentication.setCaptcha(smsRequest.getCaptcha());
        authentication.setAuthenticated(false);
        return this.getAuthenticationManager().authenticate(authentication);
    }

}