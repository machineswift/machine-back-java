package com.machine.app.admin.scm.property.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyGroupPropertyItemRequestVo {

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyId;

    @NotNull(message = "排序不能为空")
    @Schema(description = "组内排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;
}
