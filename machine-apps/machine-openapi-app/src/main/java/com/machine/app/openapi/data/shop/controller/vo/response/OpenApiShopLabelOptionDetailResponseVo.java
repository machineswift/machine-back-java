package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiShopLabelOptionDetailResponseVo {

    @Schema(description = "标签选项ID")
    private String id;

    @Schema(description = "标签ID")
    private String labelId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态")
    private StatusEnum status;
}
