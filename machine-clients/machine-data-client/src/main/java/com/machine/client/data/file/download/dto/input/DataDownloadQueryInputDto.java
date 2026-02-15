package com.machine.client.data.file.download.dto.input;

import com.machine.sdk.common.envm.data.file.DataDownloadStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataDownloadQueryInputDto {

    @Schema(description = "任务状态（ExportTaskStatusEnum）")
    private DataDownloadStatusEnum status;

    @Schema(description = "条数")
    private Integer limit = 20;
}