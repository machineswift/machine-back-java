package com.machine.client.scm.attribute.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupAttributeRelationDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "分组ID")
    private String groupId;

    @Schema(description = "属性ID")
    private String attributeId;

    @Schema(description = "组内是否必填")
    private Boolean requiredFlag;

    @Schema(description = "组内是否展示")
    private Boolean displayFlag;

    @Schema(description = "组内是否可搜索")
    private Boolean searchableFlag;

    @Schema(description = "组内排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}