package com.machine.app.manage.data.file.controller.vo.request;

import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
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
public class DownloadFilePageRequestVo extends PageRequest {

    @Schema(description = "文件名称)")
    private String fileName;

    @Schema(description = "状态枚举(ExportTaskStatusEnum)")
    private List<ExportTaskStatusEnum> statusList;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private String createEndTime;

}