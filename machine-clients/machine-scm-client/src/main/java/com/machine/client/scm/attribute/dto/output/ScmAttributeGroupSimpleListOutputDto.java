package com.machine.client.scm.attribute.dto.output;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeGroupTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupSimpleListOutputDto {

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

    @Schema(description = "组间排序，sort值大的排序靠前")
    private Long sort;
}
