package com.machine.app.admin.data.filecenter.attachment.controller.vo.request;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentLogQueryPageRequestVo extends PageRequest {

    @Schema(description = "操作人用户ID集合")
    private Set<String> userIdSet;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "姓名（模糊）")
    private String realName;

    @Schema(description = "操作来源（OperateSourceEnum）")
    private OperateSourceEnum operateSource;

    @Schema(description = "操作模块（ModuleEnum）")
    private ModuleEnum module;

    @Schema(description = "操作模块实体（ModuleEntityEnum）")
    private ModuleEntityEnum moduleEntity;

    @Schema(description = "操作模块实体ID")
    private String moduleEntityId;

    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "操作类型集合（DataAttachmentOperationTypeEnum）")
    private Set<DataAttachmentOperationTypeEnum> operationTypeSet;

    @Schema(description = "操作结果（DataAttachmentOperationResultEnum）")
    private DataAttachmentOperationResultEnum operationResult;

    @Schema(description = "客户端IP")
    private String clientIp;

    @Schema(description = "分布式链路追踪ID")
    private String traceId;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "操作开始时间")
    private Long createStartTime;

    @Schema(description = "操作结束时间")
    private Long createEndTime;
}
