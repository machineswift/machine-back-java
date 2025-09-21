package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataSuperShopCollectIdInputDto {

    @Schema(description = "收藏门店Id集合")
    private Set<String> collectShopIdSet;

    @Schema(description = "取消收藏门店Id集合")
    private Set<String> unCollectShopIdSet;
}
