package com.machine.sdk.common.context;

import com.machine.sdk.common.annotation.SkipUserIdCheck;
import com.machine.sdk.common.exception.iam.authentication.AuthInterceptorUserIdException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class HandlerInterceptorAdapter implements HandlerInterceptor {

    private final Map<String, Boolean> annotationCache = new ConcurrentHashMap<>();

    /**
     * 在请求处理之前执行的逻辑
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        AppContext.getContext().clear();
        String userId = request.getHeader(USER_ID_KEY);
        if (null == userId || userId.trim().isEmpty()) {
            String feignMethod = request.getRequestURI();

            if (shouldSkipUserIdCheck(handler)) {
                return true;
            }

            log.warn("用户Id丢失，feign method:{}", feignMethod);
            throw new AuthInterceptorUserIdException("用户Id丢失");
        }
        AppContext.getContext().setUserId(userId);
        return true;
    }

    /**
     * 在请求处理之后、视图渲染之前执行的逻辑
     */
    public void postHandle(@NotNull HttpServletRequest request,
                           @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在请求处理完成后执行的逻辑，无论是否发生异常
     */
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response,
                                @NotNull Object handler,
                                @Nullable Exception ex) throws Exception {
        AppContext.getContext().clear();
    }


    /**
     * 检查是否需要跳过用户ID验证
     */
    private boolean shouldSkipUserIdCheck(Object handler) {
        // 只处理HandlerMethod类型（Spring MVC的控制器方法）
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return false;
        }

        Method method = handlerMethod.getMethod();
        Class<?> controllerClass = handlerMethod.getBeanType();

        // 构建缓存key：类名+方法名
        String cacheKey = buildCacheKey(controllerClass, method);

        // 使用缓存避免重复反射
        return annotationCache.computeIfAbsent(cacheKey, key -> {
            // 检查方法上的注解
            if (method.isAnnotationPresent(SkipUserIdCheck.class)) {
                return true;
            }
            return false;
        });
    }

    /**
     * 构建缓存key：类名#方法名
     */
    private String buildCacheKey(Class<?> controllerClass, Method method) {
        return controllerClass.getName() + "#" + method.getName();
    }

}
