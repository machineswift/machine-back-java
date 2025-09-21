package com.machine.app.manage.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataShopQueryPageRequestVo extends PageRequest {

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

    @Schema(description = "国家编码")
    private String countryCode;

    @Schema(description = "区域编码集合")
    private Set<String> areaCodeSet;

    @Schema(description = "组织类型")
    private IamOrganizationTypeEnum organizationType;

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "标签选项ID集合")
    private Set<String> labelOptionIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
