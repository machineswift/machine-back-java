package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DataShopQueryListAllInputDto {

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

}
