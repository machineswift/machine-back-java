package com.machine.client.data.filecenter.attachment.dto.output;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentOperationLogDetailOutputDto {

    @Schema(description = "日志ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名（系统账号）")
    private String username;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "版本ID")
    private String versionId;

    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "操作来源（OperateSourceEnum）")
    private OperateSourceEnum operateSource;

    @Schema(description = "操作模块（ModuleEnum）")
    private ModuleEnum module;

    @Schema(description = "操作模块实体（ModuleEntityEnum）")
    private ModuleEntityEnum moduleEntity;

    @Schema(description = "操作模块实体Id")
    private String moduleEntityId;

    @Schema(description = "操作类型")
    private DataAttachmentOperationTypeEnum operationType;

    @Schema(description = "分布式链路追踪ID")
    private String traceId;

    @Schema(description = "客户端IP")
    private String clientIp;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "User-Agent（浏览器/客户端标识）")
    private String userAgent;

    @Schema(description = "操作结果")
    private DataAttachmentOperationResultEnum operationResult;

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "操作人ID")
    private String createBy;

    @Schema(description = "操作时间（Unix 时间戳）")
    private Long createTime;

}
