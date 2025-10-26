package com.machine.client.data.tag.dto.output;

import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagCategoryDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "画像主体类型（ProfileSubjectTypeEnum）")
    private ProfileSubjectTypeEnum type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序，sort值大的排序靠前")
    private Integer sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
