package com.machine.app.admin.scm.attribute.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeGroupTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 属性分组 + 该分组在指定后台分类下的关联配置（应用于 list_simple）
 */
@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupRelationListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "组类型（ScmAttributeGroupTypeEnum）")
    private ScmAttributeGroupTypeEnum groupType;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "该组在此后台分类下的排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "该组在此后台分类下是否必填")
    private Boolean requiredFlag;

    @Schema(description = "该组在此后台分类下是否展示")
    private Boolean displayFlag;

    @Schema(description = "该组在此后台分类下是否可搜索")
    private Boolean searchableFlag;

    @Schema(description = "该组在此后台分类下的扩展特性JSON")
    private String features;
}
