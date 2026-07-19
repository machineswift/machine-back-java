package com.machine.app.admin.scm.category.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema
public class ScmFrontBackCategoryRelationCreateRequestVo {

    @NotBlank(message = "前台分类ID不能为空")
    @Schema(description = "前台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String frontCategoryId;

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "后台分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;
}