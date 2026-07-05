package com.machine.client.scm.property.dto.output;

import com.machine.sdk.base.envm.scm.category.ScmItemInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmProperityTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "属性类型")
    private ScmProperityTypeEnum propertyType;

    @Schema(description = "是否必填")
    private Boolean isRequired;

    @Schema(description = "输入方式")
    private ScmItemInputTypeEnum inputType;

    @Schema(description = "是否多选")
    private Boolean isMultiple;

    @Schema(description = "是否可搜索")
    private Boolean isSearch;

    @Schema(description = "扩展特性JSON")
    private String features;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

    @Schema(description = "属性枚举值列表")
    private List<ScmPropertyValueListOutputDto> valueList;
}
