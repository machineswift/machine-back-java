package com.machine.starter.web.operateLog;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import lombok.Data;

/**
 * 操作日志采集上下文。
 */
@Data
public class OperationLogContext {

    // ========== 操作主体 ==========
    private String userId;

    // ========== 语义 ==========
    private OperateSourceEnum operateSource;
    private ModuleEnum module;
    private ModuleEntityEnum moduleEntity;
    private String moduleEntityId;
    private ActionTypeEnum operateType;
    private String operateName;
    private String content;

    // ========== 请求链路 & 客户端环境 ==========
    private String traceId;
    private String clientIp;
    private String platform;
    private String deviceId;
    private String userAgent;

    // ========== HTTP 请求/响应 ==========
    private String httpMethod;
    private String requestPath;
    private String queryString;
    private String requestBody;
    private Integer httpStatus;
    private String responseBody;

    // ========== 业务对象 ==========
    private String diff;

    // ========== 结果 ==========
    private ActionStatusEnum actionStatus;
    private String errorCode;
    private String errorMessage;
    private String exceptionStack;

    // ========== 性能 & 扩展 ==========
    private Long costTime;
    private Object extendInfo;
}
