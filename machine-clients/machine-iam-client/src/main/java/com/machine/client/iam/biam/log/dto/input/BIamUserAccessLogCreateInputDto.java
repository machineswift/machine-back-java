package com.machine.client.iam.biam.log.dto.input;

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
public class BIamUserAccessLogCreateInputDto {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "操作来源，对应 OperateSourceEnum")
    private OperateSourceEnum operateSource;

    @Schema(description = "操作模块，对应 ModuleEnum")
    private ModuleEnum module;

    @Schema(description = "操作模块实体，对应 ModuleEntityEnum")
    private ModuleEntityEnum moduleEntity;

    @Schema(description = "操作分类(增/删/改/查/导出等)，对应 ActionTypeEnum")
    private ActionTypeEnum operateType;

    @Schema(description = "操作名称，如：新增用户、修改密码等")
    private String operateName;

    @Schema(description = "分布式链路追踪ID")
    private String traceId;

    @Schema(description = "客户端真实IP")
    private String clientIp;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "设备ID（移动端）")
    private String deviceId;

    @Schema(description = "User-Agent（浏览器/设备信息）")
    private String userAgent;

    @Schema(description = "HTTP方法（GET/POST/PUT/DELETE/PATCH）")
    private String httpMethod;

    @Schema(description = "请求路径（不含QueryString，便于聚合统计）")
    private String requestPath;

    @Schema(description = "QueryString参数")
    private String queryString;

    @Schema(description = "请求体（敏感字段需脱敏）")
    private String requestBody;

    @Schema(description = "HTTP状态码（200/404/500等）")
    private Integer httpStatus;

    @Schema(description = "响应体（敏感字段需脱敏）")
    private String responseBody;

    @Schema(description = "业务状态，对应 ActionStatusEnum")
    private ActionStatusEnum actionStatus;

    @Schema(description = "业务错误码")
    private String errorCode;

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "异常堆栈（仅500时记录）")
    private String exceptionStack;

    @Schema(description = "接口总耗时（毫秒）")
    private Long costTime;

    @Schema(description = "扩展信息（JSONB格式）")
    private Object extendInfo;

}
