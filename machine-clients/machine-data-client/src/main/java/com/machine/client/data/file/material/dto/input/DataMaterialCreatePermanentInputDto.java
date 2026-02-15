package com.machine.client.data.file.material.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialCreatePermanentInputDto {

    @NotBlank(message = "素材Id不能为空")
    @Schema(description = "素材Id")
    private String materialId;

    @NotBlank(message = "素材标题不能为空")
    @Schema(description = "素材标题")
    private String title;

    @Schema(description = "素材分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "过期时间")
    private Long expireTime;

    @Schema(description = "描述")
    private String description;

}


