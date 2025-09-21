package com.machine.app.manage.data.brand.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandCreateRequestVo {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "LOGO 素材Id")
    private String logoMaterialId;

    @Schema(description = "描述")
    private String description;
}


