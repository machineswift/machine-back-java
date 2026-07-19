package com.machine.app.admin.scm.property.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyValueCreateRequestVo {

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyId;

    @NotBlank(message = "属性值不能为空")
    @Schema(description = "属性值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;
}
