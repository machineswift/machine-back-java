package com.machine.client.data.filecenter.attachment.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentOperationLogListOutputDto {

    @Schema(description = "日志ID")
    private String id;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "版本ID")
    private String versionId;

    @Schema(description = "操作类型")
    private String operationType;

    @Schema(description = "操作结果")
    private String operationResult;

    @Schema(description = "IP 地址")
    private String ipAddress;

    @Schema(description = "客户端平台")
    private String platform;

    @Schema(description = "用户代理")
    private String userAgent;

    @Schema(description = "错误信息")
    private String errorMsg;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
