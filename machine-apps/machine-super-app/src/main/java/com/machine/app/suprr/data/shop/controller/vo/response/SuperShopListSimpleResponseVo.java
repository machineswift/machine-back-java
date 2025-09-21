package com.machine.app.suprr.data.shop.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class SuperShopListSimpleResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "是否收藏")
    private Boolean collected;

    @Schema(description = "组织ID")
    private String organizationId;
}
