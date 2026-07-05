package com.machine.client.scm.property.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyGroupRelationCreateInputDto {

    @NotBlank(message = "分组ID不能为空")
    @Schema(description = "分组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String groupId;

    @NotBlank(message = "属性ID不能为空")
    @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String propertyId;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;
}