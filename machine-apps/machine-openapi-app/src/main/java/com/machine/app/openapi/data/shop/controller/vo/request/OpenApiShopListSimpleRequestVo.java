package com.machine.app.openapi.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiShopListSimpleRequestVo {

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

    @Schema(description = "偏移量，不传的话取默认值。下一次请求取当前结果集最后一条数据的ID。")
    private String offset;

    @Min(10)
    @Max(100)
    @NotNull(message = "分页大小不能为空")
    @Schema(description = "支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100。")
    private Integer size;

}
