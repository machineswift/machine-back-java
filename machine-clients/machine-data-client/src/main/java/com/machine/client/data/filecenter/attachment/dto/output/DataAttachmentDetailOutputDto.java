package com.machine.client.data.filecenter.attachment.dto.output;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentDetailOutputDto  {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态")
    private DataAttachmentStatusEnum status;

    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @Schema(description = "实体Id")
    private String entityId;

    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "当前版本ID")
    private String currentVersionId;

    @Schema(description = "最大版本号")
    private Integer maxVersionNo;

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


