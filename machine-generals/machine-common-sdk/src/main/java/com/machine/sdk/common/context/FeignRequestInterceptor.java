package com.machine.sdk.common.context;

import com.machine.sdk.common.exception.iam.authentication.AuthFeignUserIdException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;

@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    @SneakyThrows
    public void apply(RequestTemplate template) {
        String userId = AppContext.getContext().getUserId();
        if (null == userId || userId.trim().isEmpty()) {
            String feignMethod = template.feignTarget().name() + template.path();
            if (IGNORE_SET.contains(feignMethod)) {
                return;
            }
            log.warn("用户Id丢失，feign method:{}", feignMethod);
            throw new AuthFeignUserIdException("用户Id丢失");
        }
        template.header(USER_ID_KEY, userId);
    }

    private static final Set<String> IGNORE_SET = new HashSet<>(
            List.of("machine-iam-service/machine-iam-service/server/iam/user/detail_auth",
                    "machine-iam-service/machine-iam-service/server/iam/user/get_by_username",
                    "machine-iam-service/machine-iam-service/server/iam/user/get_by_phone",
                    "machine-iam-service/machine-iam-service/server/iam/user/get_by_thirdPartyUuid",
                    "machine-iam-service/machine-iam-service/server/oauth2_authorization"
            ));

}
