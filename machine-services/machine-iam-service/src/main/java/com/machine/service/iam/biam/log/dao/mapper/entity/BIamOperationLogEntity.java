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
@TableName(value = "t_biam_operation_log", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class BIamOperationLogEntity extends BaseEntity {

    /**
     * 操作人用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 操作人用户名
     */
    @TableField("username")
    private String username;

    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

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
     * 操作模块实体主键ID
     */
    @TableField("module_entity_id")
    private String moduleEntityId;

    /**
     * 操作分类(增/删/改/查/授权等) {@link ActionTypeEnum}
     */
    @TableField("operate_type")
    private ActionTypeEnum operateType;

    /**
     * 操作名称，如：修改用户权限
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
     * User-Agent（浏览器/客户端标识）
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * HTTP方法（GET/POST/PUT/DELETE/PATCH）
     */
    @TableField("http_method")
    private String httpMethod;

    /**
     * 请求路径
     */
    @TableField("request_path")
    private String requestPath;

    /**
     * QueryString参数
     */
    @TableField("query_string")
    private String queryString;

    /**
     * 请求体快照
     */
    @TableField("request_body")
    private String requestBody;

    /**
     * HTTP状态码（200/404/500等）
     */
    @TableField("http_status")
    private Integer httpStatus;

    /**
     * 响应体快照
     */
    @TableField("response_body")
    private String responseBody;

    /**
     * 操作内容描述
     */
    @TableField("content")
    private String content;

    /**
     * 变更diff（JSON文本），集合字段为 added/removed
     */
    @TableField("diff_json")
    private String diff;

    /**
     * 业务状态 {@link ActionStatusEnum}
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
     * 异常堆栈
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
