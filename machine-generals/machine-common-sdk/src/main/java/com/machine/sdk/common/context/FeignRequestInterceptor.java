package com.machine.sdk.common.context;

import com.machine.sdk.common.annotation.SkipUserIdCheck;
import com.machine.sdk.common.exception.iam.authentication.AuthFeignUserIdException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    private final Map<Method, Boolean> annotationCache = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public void apply(RequestTemplate template) {
        String userId = AppContext.getContext().getUserId();

        if (shouldSkipUserIdCheck(template)) {
            return;
        }

        if (null == userId || userId.trim().isEmpty()) {
            String feignMethod = template.feignTarget().name() + template.path();
            log.warn("用户Id丢失，feign method:{}", feignMethod);
            throw new AuthFeignUserIdException("用户Id丢失");
        }
        template.header(USER_ID_KEY, userId);
    }

    private boolean shouldSkipUserIdCheck(RequestTemplate template) {
        Method method = template.methodMetadata().method();
        return annotationCache.computeIfAbsent(method,
                m -> m.isAnnotationPresent(SkipUserIdCheck.class));
    }
}