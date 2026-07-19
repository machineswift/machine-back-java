package com.machine.app.admin.scm.property.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class ScmPropertyGroupUpdateRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "后台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;
}