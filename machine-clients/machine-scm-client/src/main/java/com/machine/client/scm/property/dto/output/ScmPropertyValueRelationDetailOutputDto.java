package com.machine.client.scm.property.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyValueRelationDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父属性值ID")
    private String parentValueId;

    @Schema(description = "子属性ID")
    private String childPropertyId;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;
}