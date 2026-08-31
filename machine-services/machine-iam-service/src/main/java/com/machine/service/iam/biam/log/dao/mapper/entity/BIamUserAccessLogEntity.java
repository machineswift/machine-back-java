package com.machine.service.iam.biam.log.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.mybatis.BaseEntity;
import com.machine.starter.mybatis.type.JsonbTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "t_biam_user_access_log", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class BIamUserAccessLogEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 操作来源 {@link OperateSourceEnum}
     */
    @TableField("operate_source")
    private OperateSourceEnum operateSource;

    /**
     * 操作模块 {@link ModuleEnum}
     */
    @TableField("module")
    private ModuleEnum module;

    /**
     * 操作模块实体 {@link ModuleEntityEnum}
     */
    @TableField("module_entity")
    private ModuleEntityEnum moduleEntity;

    /**
     * 操作分类 {@link ActionTypeEnum}
     */
    @TableField("operate_type")
    private ActionTypeEnum operateType;

    /**
     * 操作名称，如：新增用户、修改密码等
     */
    @TableField("operate_name")
    private String operateName;

    /**
     * 分布式链路追踪ID
     */
    @TableField("trace_id")
    private String traceId;

    /**
     * 客户端真实IP
     */
    @TableField("client_ip")
    private String clientIp;

    /**
     * 客户端平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 设备ID（移动端）
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * User-Agent（浏览器/设备信息）
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * HTTP方法（GET/POST/PUT/DELETE/PATCH）
     */
    @TableField("http_method")
    private String httpMethod;

    /**
     * 请求路径（不含QueryString，便于聚合统计）
     */
    @TableField("request_path")
    private String requestPath;

    /**
     * QueryString参数
     */
    @TableField("query_string")
    private String queryString;

    /**
     * 请求体（敏感字段需脱敏）
     */
    @TableField("request_body")
    private String requestBody;

    /**
     * HTTP状态码（200/404/500等）
     */
    @TableField("http_status")
    private Integer httpStatus;

    /**
     * 响应体（敏感字段需脱敏）
     */
    @TableField("response_body")
    private String responseBody;

    /**
     * 业务状态，对应 {@link ActionStatusEnum}
     */
    @TableField("action_status")
    private ActionStatusEnum actionStatus;

    /**
     * 业务错误码
     */
    @TableField("error_code")
    private String errorCode;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 异常堆栈（仅500时记录）
     */
    @TableField("exception_stack")
    private String exceptionStack;

    /**
     * 接口总耗时（毫秒）
     */
    @TableField("cost_time")
    private Long costTime;

    /**
     * 扩展信息（JSONB格式）
     */
    @TableField(value = "extend_info", typeHandler = JsonbTypeHandler.class)
    private Object extendInfo;

}
