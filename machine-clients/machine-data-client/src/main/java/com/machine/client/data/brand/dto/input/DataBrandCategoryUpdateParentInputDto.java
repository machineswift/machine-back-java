package com.machine.client.data.brand.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DataBrandCategoryUpdateParentInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id")
    private String id;

    @NotBlank(message = "父id不能为空")
    @Schema(description = "父id")
    private String parentId;
}
