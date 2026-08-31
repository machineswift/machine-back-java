package com.machine.starter.web.operateLog;

import lombok.Getter;

/**
 * 操作日志事件。
 */
@Getter
public class OperationLogEvent {

    private final OperationLogContext context;

    public OperationLogEvent(OperationLogContext context) {
        this.context = context;
    }
}
