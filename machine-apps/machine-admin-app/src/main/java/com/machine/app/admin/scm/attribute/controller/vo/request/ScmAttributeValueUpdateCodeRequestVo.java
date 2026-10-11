package com.machine.app.admin.scm.attribute.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeValueUpdateCodeRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "属性值ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "属性值编码不能为空")
    @Size(max = 32, message = "编码长度不能超过32个字符")
    @Schema(description = "属性值编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
