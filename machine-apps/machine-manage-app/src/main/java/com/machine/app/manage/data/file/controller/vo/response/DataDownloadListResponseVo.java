package com.machine.app.manage.data.file.controller.vo.response;

import com.machine.client.data.attachment.dto.DataAttachmentDto;
import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class DataDownloadListResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = " 任务状态（DataDownloadStatusEnum）")
    private DataDownloadStatusEnum status;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "附件信息")
    private DataAttachmentDto attachment;

    @Schema(description = "失败原因")
    private String failCause;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}