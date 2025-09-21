package com.machine.client.data.file.dto.input;

import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataDownloadUpdateInputDto {

    @Schema(description = "id")
    private String id;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "任务状态枚举(ExportTaskStatusEnum)")
    private DataDownloadStatusEnum status;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "使用次数")
    private Integer usageCount;
}