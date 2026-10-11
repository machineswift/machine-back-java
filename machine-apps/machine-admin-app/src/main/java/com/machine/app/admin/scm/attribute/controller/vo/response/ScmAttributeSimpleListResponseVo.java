package com.machine.app.admin.scm.attribute.controller.vo.response;

import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeSimpleListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "属性类型")
    private ScmAttributeTypeEnum attributeType;

    @Schema(description = "输入方式")
    private ScmAttributeInputTypeEnum inputType;
}
