package com.machine.client.data.file.dto.input;

import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryDownloadFileQueryInputDto {

    @Schema(description = "任务状态（ExportTaskStatusEnum）")
    private ExportTaskStatusEnum status;

    @Schema(description = "条数")
    private Integer limit = 20;
}