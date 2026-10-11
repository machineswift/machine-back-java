package com.machine.app.admin.scm.attribute.controller.vo.request;

import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeQueryPageRequestVo extends PageRequest {

    @Schema(description = "编码（模糊）")
    private String code;

    @Schema(description = "名称（模糊）")
    private String name;

    @Schema(description = "属性类型")
    private ScmAttributeTypeEnum attributeType;

    @Schema(description = "输入方式")
    private ScmAttributeInputTypeEnum inputType;

    @Schema(description = "是否可搜索")
    private Boolean searchableFlag;
}
