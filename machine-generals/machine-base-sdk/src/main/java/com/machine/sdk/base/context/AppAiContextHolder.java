package com.machine.sdk.base.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.machine.sdk.base.envm.ai.AiModelNameTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppAiContextHolder {

    private AiModelNameTypeEnum modelType;

    public static AppAiContextHolder getContext() {
        AppAiContextHolder result = THREAD_LOCAL.get();

        if (result == null) {
            result = new AppAiContextHolder();
            THREAD_LOCAL.set(result);
        }

        return result;
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }

    private static final ThreadLocal<AppAiContextHolder> THREAD_LOCAL = new TransmittableThreadLocal<>();
}