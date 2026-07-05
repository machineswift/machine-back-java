package com.machine.client.scm.property.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyValueCreateInputDto {

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyId;

    @NotBlank(message = "属性值不能为空")
    @Schema(description = "属性值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;
}