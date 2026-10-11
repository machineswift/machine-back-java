package com.machine.app.admin.scm.category.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class ScmFrontCategoryUpdateBackCategoryRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "后台类目ID集合（全量替换，传空集合表示清空关联）")
    private Set<String> backCategoryIdSet;
}
