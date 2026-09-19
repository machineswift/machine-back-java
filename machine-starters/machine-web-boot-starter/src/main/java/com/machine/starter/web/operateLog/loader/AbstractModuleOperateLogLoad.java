package com.machine.starter.web.operateLog.loader;

import com.machine.sdk.base.model.request.IdRequest;

import java.util.function.Function;

/**
 * 操作日志模块化快照加载器的公共基类。
 */
public abstract class AbstractModuleOperateLogLoad {

    protected final OperationLogLoaderRegistry registry;

    protected AbstractModuleOperateLogLoad(OperationLogLoaderRegistry registry) {
        this.registry = registry;
    }

    protected Object loadDetail(Object entityId,
                                Function<IdRequest, Object> detailFn) {
        if (entityId == null || detailFn == null) {
            return null;
        }
        return detailFn.apply(new IdRequest(String.valueOf(entityId)));
    }
}
