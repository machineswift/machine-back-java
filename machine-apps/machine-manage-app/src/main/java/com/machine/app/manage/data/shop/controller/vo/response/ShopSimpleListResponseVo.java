package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class ShopSimpleListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "类型（ShopTypeEnum）")
    private ShopTypeEnum type;

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "更新时间")
    private long updateTime;
}
