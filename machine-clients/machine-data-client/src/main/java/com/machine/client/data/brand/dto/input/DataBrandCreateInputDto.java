package com.machine.client.data.brand.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandCreateInputDto {

    @Schema(description = "父品牌ID，为空时挂载到根节点")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "描述")
    private String description;
}


