package com.machine.sdk.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppContext {

    private String userId;

    private String clientId;

    private String permissionCode;

    private String dataPermissionCode;

    private IamAuthMethodEnum authMethod;

    public static AppContext getContext() {
        AppContext result = THREAD_LOCAL.get();

        if (result == null) {
            result = new AppContext();
            THREAD_LOCAL.set(result);
        }

        return result;
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }

    private static final ThreadLocal<AppContext> THREAD_LOCAL = new TransmittableThreadLocal<>();
}