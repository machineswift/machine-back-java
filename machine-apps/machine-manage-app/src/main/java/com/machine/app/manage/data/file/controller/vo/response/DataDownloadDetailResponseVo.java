package com.machine.app.manage.data.file.controller.vo.response;

import com.machine.client.data.attachment.dto.DataAttachmentDto;
import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class DataDownloadDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = " 任务状态（ExportTaskStatusEnum）")
    private DataDownloadStatusEnum status;

    @Schema(description = "附件信息")
    private DataAttachmentDto attachment;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Long updateTime;

}