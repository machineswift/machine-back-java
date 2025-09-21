package com.machine.client.data.file.dto.input;

import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataDownloadQueryPageInputDto extends PageRequest {

    @Schema(description = "用户ID)")
    private String userId;

    @Schema(description = "文件名称)")
    private String fileName;

    @Schema(description = "状态枚举(ExportTaskStatusEnum)")
    private List<DataDownloadStatusEnum> statusList;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;
}