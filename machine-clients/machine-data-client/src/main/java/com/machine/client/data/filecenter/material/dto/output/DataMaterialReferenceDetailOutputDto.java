package com.machine.client.data.filecenter.material.dto.output;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialReferenceDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "素材ID")
    private String materialId;

    @Schema(description = "附件ID")
    private String attachmentId;

    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @Schema(description = "实体Id")
    private String entityId;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
