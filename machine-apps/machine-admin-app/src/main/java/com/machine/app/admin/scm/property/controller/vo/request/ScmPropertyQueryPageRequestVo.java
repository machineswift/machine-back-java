package com.machine.app.admin.scm.property.controller.vo.request;

import com.machine.sdk.base.envm.scm.category.ScmItemInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmProperityTypeEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyQueryPageRequestVo extends PageRequest {

    @Schema(description = "编码（模糊）")
    private String code;

    @Schema(description = "名称（模糊）")
    private String name;

    @Schema(description = "属性类型")
    private ScmProperityTypeEnum propertyType;

    @Schema(description = "输入方式")
    private ScmItemInputTypeEnum inputType;

    @Schema(description = "是否可搜索")
    private Boolean isSearch;
}
