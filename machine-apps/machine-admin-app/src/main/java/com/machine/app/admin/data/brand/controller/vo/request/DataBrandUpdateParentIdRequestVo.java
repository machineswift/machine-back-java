package com.machine.app.admin.data.brand.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandUpdateParentIdRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "父品牌ID不能为空")
    @Schema(description = "父品牌ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentId;

}
