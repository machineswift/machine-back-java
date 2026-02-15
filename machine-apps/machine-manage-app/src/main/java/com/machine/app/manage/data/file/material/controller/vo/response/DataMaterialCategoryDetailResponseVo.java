package com.machine.app.manage.data.file.material.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialCategoryDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Long sort;

}
