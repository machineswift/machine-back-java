package com.machine.client.data.filecenter.attachment.dto.input;

import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentOperationLogCreateInputDto {

    @NotBlank(message = "附件ID不能为空")
    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "版本ID")
    private String versionId;

    @NotNull(message = "操作类型不能为空")
    @Schema(description = "操作类型")
    private DataAttachmentOperationTypeEnum operationType;

    @NotNull(message = "操作结果不能为空")
    @Schema(description = "操作结果")
    private DataAttachmentOperationResultEnum operationResult;

    @Schema(description = "客户端IP地址")
    private String ipAddress;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "用户代理")
    private String userAgent;

    @Schema(description = "错误信息")
    private String errorMsg;
}
