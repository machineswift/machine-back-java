package com.machine.starter.web.operateLog.diff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParentChangeSnapshot {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "父节点名称")
    private String parentName;

}
