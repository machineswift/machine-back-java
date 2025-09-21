package com.machine.client.data.attachment.dto;

import com.machine.sdk.common.envm.data.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（DataAttachmentStatusEnum）")
    private DataAttachmentStatusEnum status;

    @Schema(description = "类型（DataAttachmentTypeEnum）")
    private DataAttachmentTypeEnum type;

    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "大小（字节）")
    private Long size;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}


