package com.machine.starter.web.accessLog;

import lombok.Getter;

/**
 * 访问日志事件。
 */
@Getter
public class ApiAccessLogEvent {

    private final ApiAccessLogContext context;

    public ApiAccessLogEvent(ApiAccessLogContext context) {
        this.context = context;
    }
}
