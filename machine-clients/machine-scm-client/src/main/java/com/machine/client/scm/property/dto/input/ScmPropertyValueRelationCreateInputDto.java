package com.machine.client.scm.property.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyValueRelationCreateInputDto {

    @NotBlank(message = "父属性值ID不能为空")
    @Schema(description = "父属性值ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentValueId;

    @NotBlank(message = "子属性ID不能为空")
    @Schema(description = "子属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String childPropertyId;
}