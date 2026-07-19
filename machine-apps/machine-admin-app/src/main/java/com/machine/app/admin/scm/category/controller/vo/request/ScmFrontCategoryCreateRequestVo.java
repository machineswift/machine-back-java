package com.machine.app.admin.scm.category.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Schema
public class ScmFrontCategoryCreateRequestVo {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "父ID不能为空")
    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentId;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "后台类目ID集合")
    private Set<String> backCategoryIdSet;
}