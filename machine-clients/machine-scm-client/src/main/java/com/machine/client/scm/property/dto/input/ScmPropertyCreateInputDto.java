package com.machine.client.scm.property.dto.input;

import com.machine.sdk.base.envm.scm.category.ScmItemInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmProperityTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyCreateInputDto {

    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "属性类型不能为空")
    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScmProperityTypeEnum propertyType;

    @NotNull(message = "是否必填不能为空")
    @Schema(description = "是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isRequired;

    @NotNull(message = "输入方式不能为空")
    @Schema(description = "输入方式", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScmItemInputTypeEnum inputType;

    @NotNull(message = "是否多选不能为空")
    @Schema(description = "是否多选", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isMultiple;

    @NotNull(message = "是否可搜索不能为空")
    @Schema(description = "是否可搜索", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSearch;

    @NotNull(message = "是否多选不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;

    @Schema(description = "扩展特性JSON", requiredMode = Schema.RequiredMode.REQUIRED)
    private String features;

}