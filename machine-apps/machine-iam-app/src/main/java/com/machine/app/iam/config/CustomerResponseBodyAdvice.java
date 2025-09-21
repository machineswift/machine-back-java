package com.machine.app.iam.config;

import com.machine.sdk.common.model.AppResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class  CustomerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final HttpServletRequest request;

    public CustomerResponseBodyAdvice(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(@NotNull MethodParameter methodParameter,
                            @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return !isSwaggerRequest(request);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o,
                                  @NotNull MethodParameter methodParameter,
                                  @NotNull MediaType mediaType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NotNull ServerHttpRequest serverHttpRequest,
                                  @NotNull ServerHttpResponse serverHttpResponse) {
        if(o instanceof AppResult){
            return o;
        }
        return AppResult.success(o);
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri.contains("/v3/api-docs")
                || requestUri.contains("/swagger-ui/");
    }
}