package com.machine.client.scm.attribute.dto.input;

import com.machine.sdk.base.envm.scm.category.ScmAttributeGroupTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupCreateInputDto {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "组类型不能为空")
    @Schema(description = "组类型（ScmAttributeGroupTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScmAttributeGroupTypeEnum groupType;

    @Schema(description = "扩展特性JSON")
    private String features;
}