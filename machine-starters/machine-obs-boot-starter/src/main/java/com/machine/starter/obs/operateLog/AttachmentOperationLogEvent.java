package com.machine.starter.obs.operateLog;

import lombok.Getter;

/**
 * 附件操作日志事件。
 */
@Getter
public class AttachmentOperationLogEvent {

    private final AttachmentOperationLogContext context;

    public AttachmentOperationLogEvent(AttachmentOperationLogContext context) {
        this.context = context;
    }
}
