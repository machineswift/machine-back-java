package com.machine.client.data.filecenter.attachment.dto.output;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentChangeTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentVersionDetailOutputDto {

    @Schema(description = "版本主键ID")
    private String id;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "版本状态")
    private DataAttachmentStatusEnum status;

    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @Schema(description = "实体Id")
    private String entityId;

    @Schema(description = "附件分组")
    private String attachmentGroup;

    @Schema(description = "版本号")
    private Integer versionNo;

    @Schema(description = "是否当前版本")
    private Integer isCurrent;

    @Schema(description = "来源版本ID")
    private String sourceVersionId;

    @Schema(description = "变更类型")
    private DataAttachmentChangeTypeEnum changeType;

    @Schema(description = "变更发生时间")
    private Long changeTime;

    @Schema(description = "变更说明")
    private String changeDesc;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
