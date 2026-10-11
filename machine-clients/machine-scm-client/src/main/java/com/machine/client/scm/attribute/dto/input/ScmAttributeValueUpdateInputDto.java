package com.machine.client.scm.attribute.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeValueUpdateInputDto {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "属性值名称不能为空")
    @Schema(description = "属性值名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "属性值实际值")
    private String value;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;
}