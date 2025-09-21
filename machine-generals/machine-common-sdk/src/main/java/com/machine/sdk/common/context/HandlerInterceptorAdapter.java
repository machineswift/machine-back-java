package com.machine.sdk.common.context;

import com.machine.sdk.common.exception.iam.authentication.AuthInterceptorUserIdException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class HandlerInterceptorAdapter implements HandlerInterceptor {

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
            if (IGNORE_SET.contains(feignMethod)) {
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



    private static final Set<String> IGNORE_SET = new HashSet<>(
            List.of("/machine-iam-service/server/iam/user/detail_auth",
                    "/machine-iam-service/server/iam/user/get_by_username",
                    "/machine-iam-service/server/iam/user/get_by_phone",
                    "/machine-iam-service/server/iam/user/get_by_thirdPartyUuid",
                    "/machine-iam-service/machine-iam-service/server/oauth2_authorization"));


}
