package com.machine.app.admin.scm.property.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyGroupListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "后台分类ID")
    private String backCategoryId;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "状态（1正常 0禁用）")
    private Integer status;
}