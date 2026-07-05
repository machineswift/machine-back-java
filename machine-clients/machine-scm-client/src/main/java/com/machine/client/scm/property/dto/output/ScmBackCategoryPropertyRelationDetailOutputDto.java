package com.machine.client.scm.property.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryPropertyRelationDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "后台分类ID")
    private String backCategoryId;

    @Schema(description = "属性ID")
    private String propertyId;

    @Schema(description = "分组ID")
    private String groupId;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;
}