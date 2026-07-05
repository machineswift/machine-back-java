package com.machine.client.scm.property.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyGroupDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "后台分类ID")
    private String backCategoryId;

    @Schema(description = "分组名称")
    private String name;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;
}