package com.machine.client.data.brand.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DataBrandCategoryUpdateInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id")
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Long sort;
}
