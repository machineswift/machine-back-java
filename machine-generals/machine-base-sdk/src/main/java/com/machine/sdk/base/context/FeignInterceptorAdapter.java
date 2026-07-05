package com.machine.sdk.base.context;

import cn.hutool.core.util.StrUtil;
import com.machine.sdk.base.annotation.SkipUserIdCheck;
import com.machine.sdk.base.envm.ai.AiModelNameTypeEnum;
import com.machine.sdk.base.exception.iam.authentication.AuthInterceptorUserIdException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.machine.sdk.base.constant.ContextConstant.AI_MODEL_CODE;
import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class FeignInterceptorAdapter implements HandlerInterceptor {

    private final Map<String, Boolean> annotationCache = new ConcurrentHashMap<>();

    /**
     * 在请求处理之前执行的逻辑
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        AppContextHolder.getContext().clear();
        {
            String userId = request.getHeader(USER_ID_KEY);
            if (null == userId || userId.trim().isEmpty()) {
                String feignMethod = request.getRequestURI();

                if (shouldSkipUserIdCheck(handler)) {
                    return true;
                }

                log.warn("用户Id丢失，feign method:{}", feignMethod);
                throw new AuthInterceptorUserIdException("用户Id丢失");
            }
            AppContextHolder.getContext().setUserId(userId);
            MDC.put(USER_ID_KEY, AppContextHolder.getContext().getUserId());
        }

        {
            String model_type = request.getHeader(AI_MODEL_CODE);
            if (StrUtil.isNotBlank(model_type)) {
                AiModelNameTypeEnum modelType = AiModelNameTypeEnum.valueOf(model_type);
                AppAiContextHolder.getContext().setModelType(modelType);
            }
        }
        return true;
    }

    /**
     * 在请求处理之后、视图渲染之前执行的逻辑
     */
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler,
                           ModelAndView modelAndView) {
    }

    /**
     * 在请求处理完成后执行的逻辑，无论是否发生异常
     */
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        AppContextHolder.getContext().clear();
        AppAiContextHolder.getContext().clear();
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
            return method.isAnnotationPresent(SkipUserIdCheck.class);
        });
    }

    /**
     * 构建缓存key：类名#方法名
     */
    private String buildCacheKey(Class<?> controllerClass, Method method) {
        return controllerClass.getName() + "#" + method.getName();
    }

}
