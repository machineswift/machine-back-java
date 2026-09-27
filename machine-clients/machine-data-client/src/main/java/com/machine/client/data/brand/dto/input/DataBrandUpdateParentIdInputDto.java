package com.machine.client.data.brand.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandUpdateParentIdInputDto {

    @NotBlank(message = "品牌ID不能为空")
    @Schema(description = "品牌ID")
    private String id;

    @NotBlank(message = "父品牌ID不能为空")
    @Schema(description = "父品牌ID，移动到根节点时传 root")
    private String parentId;
}
