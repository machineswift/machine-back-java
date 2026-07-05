package com.machine.sdk.base.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.machine.sdk.base.envm.iam.auth.IamAuthMethodEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppContextHolder {

    private String userId;

    private String clientId;

    private String permissionCode;

    private String dataPermissionCode;

    private IamAuthMethodEnum authMethod;

    public static AppContextHolder getContext() {
        AppContextHolder result = THREAD_LOCAL.get();

        if (result == null) {
            result = new AppContextHolder();
            THREAD_LOCAL.set(result);
        }

        return result;
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }

    private static final ThreadLocal<AppContextHolder> THREAD_LOCAL = new TransmittableThreadLocal<>();
}