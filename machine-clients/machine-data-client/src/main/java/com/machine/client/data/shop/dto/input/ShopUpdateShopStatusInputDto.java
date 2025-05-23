package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema
public class ShopUpdateShopStatusInputDto {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

}
