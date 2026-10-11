package com.machine.client.scm.attribute.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryAttributeRelationCreateInputDto {

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "后台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attributeId;

    @NotNull(message = "此类目下是否必填不能为空")
    @Schema(description = "此类目下是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean requiredFlag;

    @NotNull(message = "此类目下是否展示不能为空")
    @Schema(description = "此类目下是否展示", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean displayFlag;

    @NotNull(message = "此类目下是否可搜索不能为空")
    @Schema(description = "此类目下是否可搜索", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean searchableFlag;

    @Schema(description = "分组ID")
    private String groupId;
}