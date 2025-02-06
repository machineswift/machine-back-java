package com.machine.sdk.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;

public class AppContext {

    private String userId;

    private String clientId;

    private AuthMethodEnum authMethod;

    public static AppContext getContext() {
        AppContext result = THREAD_LOCAL.get();

        if (result == null) {
            result = new AppContext();
            THREAD_LOCAL.set(result);
        }

        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public AuthMethodEnum getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(AuthMethodEnum authMethod) {
        this.authMethod = authMethod;
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }

    private static final ThreadLocal<AppContext> THREAD_LOCAL = new TransmittableThreadLocal<>();
}
