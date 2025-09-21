package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class DataShopQueryPageInputDto extends PageRequest {

    @Schema(description = "门店ID集合")
    private Set<String> shopIdSet;

    @Schema(description = "关键字（名称/编码）")
    private String keyword;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "经营状态集合(DataShopBusinessStatusEnum)")
    private Set<DataShopBusinessStatusEnum> businessStatusSet;

    @Schema(description = "运营状态集合（DataShopOperationStatusEnum）")
    private Set<DataShopOperationStatusEnum> operationStatusSet;

    @Schema(description = "物理状态集合（DataShopPhysicalStatusEnum）")
    private Set<DataShopPhysicalStatusEnum> physicalStatusSet;

    @Schema(description = "区域编码集合")
    public Set<String> areaCodeSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
