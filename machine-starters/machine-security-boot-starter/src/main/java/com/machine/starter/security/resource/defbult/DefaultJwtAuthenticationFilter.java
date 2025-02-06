package com.machine.starter.security.resource.defbult;

import com.machine.starter.security.exception.DefaultAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;


public class DefaultJwtAuthenticationFilter extends OncePerRequestFilter {

    public DefaultJwtAuthenticationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        throw new DefaultAuthenticationException("接口地址未配置认证机制");
    }
}
