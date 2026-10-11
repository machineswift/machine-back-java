package com.machine.client.scm.attribute.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryAttributeRelationDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "后台分类ID")
    private String backCategoryId;

    @Schema(description = "属性ID")
    private String attributeId;

    @Schema(description = "此类目下是否必填")
    private Boolean requiredFlag;

    @Schema(description = "此类目下是否展示")
    private Boolean displayFlag;

    @Schema(description = "此类目下是否可搜索")
    private Boolean searchableFlag;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;

    @Schema(description = "分组ID")
    private String groupId;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;
}