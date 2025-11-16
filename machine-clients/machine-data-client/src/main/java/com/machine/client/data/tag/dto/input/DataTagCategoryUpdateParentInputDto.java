package com.machine.client.data.tag.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagCategoryUpdateParentInputDto {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "父ID不能为空")
    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentId;
}

