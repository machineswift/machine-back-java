package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiShopListSimpleResponseVo {

    @Schema(description = "门店ID")
    private String id;

    @Schema(description = "门店名称")
    private String name;

    @Schema(description = "门店编码")
    private String code;

    @Schema(description = "门店类型(ShopTypeEnum)")
    private ShopTypeEnum type;

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;
}
