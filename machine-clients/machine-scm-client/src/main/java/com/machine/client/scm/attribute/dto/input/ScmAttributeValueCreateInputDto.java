package com.machine.client.scm.attribute.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeValueCreateInputDto {

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attributeId;

    @NotBlank(message = "属性值编码不能为空")
    @Schema(description = "属性值编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "属性值名称不能为空")
    @Schema(description = "属性值名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "属性值实际值")
    private String value;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;
}