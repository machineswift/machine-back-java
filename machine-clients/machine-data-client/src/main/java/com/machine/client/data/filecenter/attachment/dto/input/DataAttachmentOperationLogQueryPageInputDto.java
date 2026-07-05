package com.machine.client.data.filecenter.attachment.dto.input;

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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataAttachmentOperationLogQueryPageInputDto extends PageRequest {

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "版本ID")
    private String versionId;

    @Schema(description = "操作类型集合")
    private Set<DataAttachmentOperationTypeEnum> operationTypeSet;

    @Schema(description = "操作结果")
    private DataAttachmentOperationResultEnum operationResult;

    @Schema(description = "IP地址")
    private String ipAddress;

    @Schema(description = "平台")
    private String platform;

    @Schema(description = "请求追踪ID")
    private String requestId;

    @Schema(description = "分布式链路追踪ID")
    private String traceId;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
