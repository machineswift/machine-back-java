package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiShopOrganizationResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "门店ID")
    private String shopId;

    @Schema(description = "组织ID")
    private String organizationId;

    @Schema(description = " 组织类型(OrganizationTypeEnum)")
    private OrganizationTypeEnum organizationType;

    @Schema(description = "排序")
    private Long sort;
}
