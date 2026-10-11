package com.machine.client.scm.attribute.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupBackCategoryItemDto {

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "后台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;

    @NotNull(message = "是否必填不能为空")
    @Schema(description = "该组在此类目下是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean requiredFlag;

    @NotNull(message = "是否展示不能为空")
    @Schema(description = "该组在此类目下是否展示", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean displayFlag;

    @NotNull(message = "是否可搜索不能为空")
    @Schema(description = "该组在此类目下是否可搜索", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean searchableFlag;

    @NotNull(message = "排序不能为空")
    @Schema(description = "类目下属性组排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;
}
