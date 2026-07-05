
package com.machine.starter.security.filter;

import com.machine.sdk.base.context.AppContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AppContextClearFilter extends GenericFilterBean {

    public AppContextClearFilter() {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            AppContextHolder.getContext().clear();
        }
    }
}
