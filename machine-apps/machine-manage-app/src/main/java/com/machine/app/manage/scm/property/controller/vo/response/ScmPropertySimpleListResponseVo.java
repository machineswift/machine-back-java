package com.machine.app.manage.scm.property.controller.vo.response;

import com.machine.sdk.base.envm.scm.category.ScmItemInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmProperityTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertySimpleListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "属性类型")
    private ScmProperityTypeEnum propertyType;

    @Schema(description = "输入方式")
    private ScmItemInputTypeEnum inputType;
}
