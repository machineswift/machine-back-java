package com.machine.client.scm.attribute.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupItemOutputDto {

    @Schema(description = "属性分组ID")
    private String groupId;

    @Schema(description = "属性分组名称")
    private String groupName;

    @Schema(description = "属性分组编码")
    private String groupCode;

    @Schema(description = "该组在此类目下是否必填")
    private Boolean requiredFlag;

    @Schema(description = "该组在此类目下是否展示")
    private Boolean displayFlag;

    @Schema(description = "该组在此类目下是否可搜索")
    private Boolean searchableFlag;

    @Schema(description = "类目下属性组排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "扩展特性JSON")
    private String features;
}
