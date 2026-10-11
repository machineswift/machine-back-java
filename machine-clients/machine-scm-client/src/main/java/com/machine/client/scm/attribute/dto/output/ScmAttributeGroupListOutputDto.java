package com.machine.client.scm.attribute.dto.output;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeGroupTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ScmAttributeGroupListOutputDto {

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

    @Schema(description = "扩展特性JSON")
    private String features;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}