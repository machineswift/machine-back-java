package com.machine.client.data.file.material.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialUpdateCategoryInputDto {

    @NotBlank(message = "素材ID不能为空")
    @Schema(description = "素材ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "所属分类ID集合，可多选")
    private Set<String> categoryIdSet;

}
