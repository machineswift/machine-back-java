package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiShopListSimpleResponseVo {

    @Schema(description = "门店ID")
    private String id;

    @Schema(description = "门店编码")
    private String code;

    @Schema(description = "门店名称")
    private String name;

    @Schema(description = "经营状态（DataShopBusinessStatusEnum）")
    private DataShopBusinessStatusEnum businessStatus;

    @Schema(description = "运营状态（DataShopOperationStatusEnum）")
    private DataShopOperationStatusEnum operationStatus;

    @Schema(description = "物理状态（DataShopPhysicalStatusEnum）")
    private DataShopPhysicalStatusEnum physicalStatus;
}
