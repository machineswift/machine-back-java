package com.machine.app.manage.data.tag.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagUpdateCategoryRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "分类ID不能为空")
    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryId;
}
