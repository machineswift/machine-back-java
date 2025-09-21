package com.machine.client.data.franchisee.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataFranchiseeShopRelationOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "加盟商id")
    private String franchiseeId;

    @Schema(description = "门店id")
    private String shopId;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}
