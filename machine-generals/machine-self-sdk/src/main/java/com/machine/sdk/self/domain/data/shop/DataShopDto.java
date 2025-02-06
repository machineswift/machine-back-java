package com.machine.sdk.self.domain.data.shop;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataShopDto {

    private String id;

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

    public DataShopDto(String id,
                       ShopStatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
