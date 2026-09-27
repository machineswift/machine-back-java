package com.machine.starter.web.accessLog;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import lombok.Data;

import java.util.Map;

/**
 * 访问日志采集上下文。
 */
@Data
public class ApiAccessLogContext {

    // ========== 注解元数据 ==========
    private OperateSourceEnum operateSource;
    private ModuleEnum module;
    private ModuleEntityEnum moduleEntity;
    private ActionTypeEnum operateType;
    private String operateName;

    // ========== 用户 & 链路 ==========
    private String userId;
    private String traceId;

    // ========== 客户端环境 ==========
    private String clientIp;
    private String platform;
    private String userAgent;
    private String deviceId;

    // ========== 请求信息 ==========
    private String httpMethod;
    private String requestPath;
    private String queryString;
    private String requestBody;

    // ========== 响应信息 ==========
    private String responseBody;
    private Integer httpStatus;

    // ========== 结果信息 ==========
    private ActionStatusEnum actionStatus;
    private String errorCode;
    private String errorMessage;
    private String exceptionStack;

    // ========== 性能 ==========
    private Long costTime;

    // ========== 扩展 ==========
    private Map<String, Object> extendInfo;
}
