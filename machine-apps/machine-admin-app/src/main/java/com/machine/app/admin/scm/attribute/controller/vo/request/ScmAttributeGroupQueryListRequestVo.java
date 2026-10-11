package com.machine.app.admin.scm.attribute.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupQueryListRequestVo {

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "所属后台类目ID（叶子类目）", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;
}
