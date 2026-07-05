package com.machine.starter.security.resource.defbult;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class DefaultJwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccessDeniedHandler accessDeniedHandler;

    public DefaultJwtAuthenticationFilter(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        accessDeniedHandler.handle(request, response,
                new AccessDeniedException("接口地址未配置认证机制"));
    }
}
