package com.machine.app.admin.scm.attribute.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeValueUpdateRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "属性值名称不能为空")
    @Size(max = 32, message = "名称长度不能超过32个字符")
    @Schema(description = "属性值名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 256, message = "属性值长度不能超过256个字符")
    @Schema(description = "属性值实际值")
    private String value;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;
}
