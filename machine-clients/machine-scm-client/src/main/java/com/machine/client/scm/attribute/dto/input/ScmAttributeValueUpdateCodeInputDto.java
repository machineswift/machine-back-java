package com.machine.client.scm.attribute.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeValueUpdateCodeInputDto {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "属性值ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "属性值编码不能为空")
    @Schema(description = "属性值编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
