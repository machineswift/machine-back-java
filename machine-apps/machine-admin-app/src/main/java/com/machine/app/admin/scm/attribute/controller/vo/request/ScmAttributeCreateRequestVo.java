package com.machine.app.admin.scm.attribute.controller.vo.request;

import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class ScmAttributeCreateRequestVo {

    @NotBlank(message = "名称不能为空")
    @Size(max = 32, message = "名称长度不能超过32个字符")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "属性类型不能为空")
    @Schema(description = "属性类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScmAttributeTypeEnum attributeType;

    @NotNull(message = "是否必填不能为空")
    @Schema(description = "是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean requiredFlag;

    @NotNull(message = "输入方式不能为空")
    @Schema(description = "输入方式", requiredMode = Schema.RequiredMode.REQUIRED)
    private ScmAttributeInputTypeEnum inputType;

    @NotNull(message = "是否多选不能为空")
    @Schema(description = "是否多选", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean multipleFlag;

    @NotNull(message = "是否可搜索不能为空")
    @Schema(description = "是否可搜索", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean searchableFlag;

    @Schema(description = "扩展特性JSON", requiredMode = Schema.RequiredMode.REQUIRED)
    private String features;
}