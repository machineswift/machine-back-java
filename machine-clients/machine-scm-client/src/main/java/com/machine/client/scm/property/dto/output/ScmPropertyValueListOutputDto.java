package com.machine.client.scm.property.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmPropertyValueListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "属性ID")
    private String propertyId;

    @Schema(description = "属性值")
    private String value;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;
}