package com.machine.client.data.file.attachment.dto.output;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.data.file.DataAttachmentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentDetailOutputDto  {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（DataAttachmentStatusEnum）")
    private DataAttachmentStatusEnum status;

    @Schema(description = "实体（ModuleEntityEnum）")
    private ModuleEntityEnum entity;

    @Schema(description = "实体Id")
    private String entityId;

    @Schema(description = "文件ID")
    private String fileId;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}


