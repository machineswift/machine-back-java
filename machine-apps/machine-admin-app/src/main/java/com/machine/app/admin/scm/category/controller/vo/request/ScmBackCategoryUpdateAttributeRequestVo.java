package com.machine.app.admin.scm.category.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class ScmBackCategoryUpdateAttributeRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Valid
    @NotNull(message = "属性列表不能为空")
    @Schema(description = "属性列表（全量替换，传空列表表示清空关联）", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AttributeItem> attributeItemList;

    @Data
    @Schema
    @NoArgsConstructor
    public static class AttributeItem {

        @NotBlank(message = "属性ID不能为空")
        @Schema(description = "属性ID", requiredMode = Schema.RequiredMode.REQUIRED)
        private String attributeId;

        @NotNull(message = "是否必填不能为空")
        @Schema(description = "该属性在此类目下是否必填", requiredMode = Schema.RequiredMode.REQUIRED)
        private Boolean requiredFlag;

        @NotNull(message = "是否展示不能为空")
        @Schema(description = "该属性在此类目下是否展示", requiredMode = Schema.RequiredMode.REQUIRED)
        private Boolean displayFlag;

        @NotNull(message = "是否可搜索不能为空")
        @Schema(description = "该属性在此类目下是否可搜索", requiredMode = Schema.RequiredMode.REQUIRED)
        private Boolean searchableFlag;

        @NotNull(message = "排序不能为空")
        @Schema(description = "类目下属性排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long sort;

        @Schema(description = "扩展特性JSON")
        private String features;
    }
}
