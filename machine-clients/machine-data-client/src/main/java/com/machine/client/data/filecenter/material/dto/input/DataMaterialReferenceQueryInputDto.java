package com.machine.client.data.filecenter.material.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialReferenceQueryInputDto {

    @NotBlank(message = "实体不能为空")
    @Schema(description = "实体")
    private String entity;

    @NotBlank(message = "实体Id不能为空")
    @Schema(description = "实体Id")
    private String entityId;
}
