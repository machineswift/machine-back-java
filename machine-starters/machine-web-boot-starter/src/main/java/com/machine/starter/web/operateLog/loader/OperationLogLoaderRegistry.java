package com.machine.starter.web.operateLog.loader;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作日志业务快照加载器注册表
 */
public class OperationLogLoaderRegistry {

    private final Map<ModuleEntityEnum, Map<String, OperationLogEntityLoader>> loaders = new ConcurrentHashMap<>();


    public void register(ModuleEntityEnum moduleEntity,
                         String methodName,
                         OperationLogEntityLoader loader) {
        if (moduleEntity == null || loader == null) {
            return;
        }
        loaders.computeIfAbsent(moduleEntity, _ -> new ConcurrentHashMap<>()).put(methodName, loader);
    }


    public OperationLogEntityLoader get(ModuleEntityEnum moduleEntity,
                                        String methodName) {
        if (moduleEntity == null) {
            return null;
        }
        Map<String, OperationLogEntityLoader> byMethod = loaders.get(moduleEntity);
        if (byMethod == null || byMethod.isEmpty()) {
            return null;
        }
        return byMethod.get(methodName);
    }

}
