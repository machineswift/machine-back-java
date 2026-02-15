package com.machine.client.data.file.download.dto.output;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.envm.data.file.DataDownloadStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataDownloadListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "任务状态")
    private DataDownloadStatusEnum status;

    @Schema(description = "模块")
    private ModuleEnum module;

    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "附件Id")
    private String attachmentId;

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