package com.machine.starter.web.operateLog.loader;

/**
 * 操作日志业务快照加载器
 */
@FunctionalInterface
public interface OperationLogEntityLoader {

    /**
     * 按业务主键加载当前快照
     */
    Object load(Object bizId);
}
