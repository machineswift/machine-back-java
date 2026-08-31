package com.machine.starter.web.accessLog;

import com.machine.starter.web.WebProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * 访问日志请求包装过滤器。
 */
@Slf4j
public class ApiAccessLogFilter extends OncePerRequestFilter {

    private final WebProperties properties;

    public ApiAccessLogFilter(WebProperties properties) {
        this.properties = properties;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        WebProperties.AccessLog accessLog = properties.getAccessLog();
        if (accessLog == null || !accessLog.isEnabled()) {
            return true;
        }
        // 文件上传类请求不做请求体缓存
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, Integer.MAX_VALUE);
        filterChain.doFilter(requestWrapper, response);
    }
}
