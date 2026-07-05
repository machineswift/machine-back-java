package com.machine.app.manage.scm.property.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyGroupListByBackCategoryRequestVo {

    @NotBlank(message = "后台分类ID不能为空")
    @Schema(description = "后台叶子类目ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String backCategoryId;
}
