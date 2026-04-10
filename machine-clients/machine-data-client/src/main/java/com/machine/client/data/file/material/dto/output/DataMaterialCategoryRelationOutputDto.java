package com.machine.client.data.file.material.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialCategoryRelationOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "素材ID")
    private String materialId;

    @Schema(description = "分类ID")
    private String categoryId;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
