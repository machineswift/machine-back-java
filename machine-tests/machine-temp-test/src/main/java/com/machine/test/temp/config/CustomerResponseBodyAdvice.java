package com.machine.test.temp.config;

import com.machine.sdk.common.model.AppResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class CustomerResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private HttpServletRequest request;

    public CustomerResponseBodyAdvice(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        if (isSwaggerRequest(request)) {
            return false;
        }
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
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