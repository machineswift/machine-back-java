package com.machine.app.iam.biam.log.controller.vo.response;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserAccessLogDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "操作来源")
    private OperateSourceEnum operateSource;

    @Schema(description = "操作模块")
    private ModuleEnum module;

    @Schema(description = "操作模块实体")
    private ModuleEntityEnum moduleEntity;

    @Schema(description = "操作分类")
    private ActionTypeEnum operateType;

    @Schema(description = "操作名称")
    private String operateName;

    @Schema(description = "分布式链路追踪ID")
    private String traceId;

    @Schema(description = "客户端真实IP")
    private String clientIp;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "User-Agent（浏览器/设备信息）")
    private String userAgent;

    @Schema(description = "HTTP方法")
    private String httpMethod;

    @Schema(description = "请求路径")
    private String requestPath;

    @Schema(description = "QueryString参数")
    private String queryString;

    @Schema(description = "请求体（敏感字段已脱敏）")
    private String requestBody;

    @Schema(description = "HTTP状态码")
    private Integer httpStatus;

    @Schema(description = "响应体（敏感字段已脱敏）")
    private String responseBody;

    @Schema(description = "业务状态，对应 ActionStatusEnum")
    private ActionStatusEnum actionStatus;

    @Schema(description = "业务错误码")
    private String errorCode;

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "异常堆栈")
    private String exceptionStack;

    @Schema(description = "接口总耗时（毫秒）")
    private Long costTime;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

}
