package com.machine.app.admin.scm.category.controller.vo.response;

import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupRelationListResponseVo;
import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "前台类目ID集合")
    private Set<String> frontCategoryIdSet;

    @Schema(description = "关联的属性分组列表")
    private List<ScmAttributeGroupRelationListResponseVo> attributeGroupList;

    @Schema(description = "关联的属性列表")
    private List<AttributeItem> attributeList;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

    @Data
    @Schema
    @NoArgsConstructor
    public static class AttributeItem {

        @Schema(description = "属性ID")
        private String attributeId;

        @Schema(description = "属性编码")
        private String code;

        @Schema(description = "属性名称")
        private String name;

        @Schema(description = "属性类型")
        private ScmAttributeTypeEnum attributeType;

        @Schema(description = "输入方式")
        private ScmAttributeInputTypeEnum inputType;

        @Schema(description = "此类目下是否必填")
        private Boolean requiredFlag;

        @Schema(description = "此类目下是否展示")
        private Boolean displayFlag;

        @Schema(description = "此类目下是否可搜索")
        private Boolean searchableFlag;

        @Schema(description = "排序，sort值大的排序靠前")
        private Long sort;

        @Schema(description = "扩展特性JSON")
        private String features;
    }
}