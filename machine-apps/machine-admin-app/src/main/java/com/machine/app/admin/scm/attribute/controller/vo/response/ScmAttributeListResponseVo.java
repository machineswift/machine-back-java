package com.machine.app.admin.scm.attribute.controller.vo.response;

import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeListResponseVo {

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

    @Schema(description = "是否必填")
    private Boolean requiredFlag;

    @Schema(description = "是否多选")
    private Boolean multipleFlag;

    @Schema(description = "是否可搜索")
    private Boolean searchableFlag;

    @Schema(description = "关联的属性分组数")
    private Integer attributeGroupNumber;

    @Schema(description = "关联的后台分类数")
    private Integer backCategoryNumber;

    @Schema(description = "扩展特性JSON")
    private String features;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}