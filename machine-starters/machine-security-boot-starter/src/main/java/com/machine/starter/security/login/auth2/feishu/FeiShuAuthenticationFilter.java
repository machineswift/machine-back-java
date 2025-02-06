package com.machine.starter.security.login.auth2.feishu;

import com.machine.starter.security.login.auth2.Auth2AuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class FeiShuAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public FeiShuAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
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
                                                HttpServletResponse response) {
        Auth2AuthenticationToken authentication = new Auth2AuthenticationToken();
        authentication.setCode(request.getParameter("code"));
        authentication.setState(request.getParameter("state"));
        authentication.setAuthenticated(false);
        return this.getAuthenticationManager().authenticate(authentication);
    }

}