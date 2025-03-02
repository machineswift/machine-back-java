package com.machine.client.data.file.dto.input;

import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDownloadFileClientInputDto {

    @Schema(description = "id")
    private String id;

    @Schema(description = "附件信息")
    private MaterialDto material;

    @Schema(description = "任务状态枚举(ExportTaskStatusEnum)")
    private ExportTaskStatusEnum status;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "重试状态")
    private Integer retryStatus;

    @Schema(description = "使用次数")
    private Integer usageCount;

}