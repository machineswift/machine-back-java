package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema
public class SuperShopCollectIdInputDto {

    @Schema(description = "收藏门店Id集合")
    private Set<String> collectShopIdSet;

    @Schema(description = "取消收藏门店Id集合")
    private Set<String> unCollectShopIdSet;
}
