package com.machine.app.manage.data.file.download.controller.vo.response;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.envm.data.file.DataDownloadStatusEnum;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class DataDownloadDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = " 任务状态（ExportTaskStatusEnum）")
    private DataDownloadStatusEnum status;

    @Schema(description = "模块(ModuleEnum)")
    private ModuleEnum module;

    @Schema(description = "实体(ModuleEntityEnum)")
    private ModuleEntityEnum entity;



    @Schema(description = "附件Id")
    private String attachmentId;

    @Schema(description = "文件类型(DataFileTypeEnum)")
    private DataFileTypeEnum fileType;

    @Schema(description = "原始名称")
    private String attachmentOriginalName;

    @Schema(description = "大小（字节）")
    private Long attachmentSize;



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