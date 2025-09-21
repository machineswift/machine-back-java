package com.machine.app.suprr.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema
public class SuperShopCollectRequestVo {

    @Schema(description = "收藏门店Id集合")
    private Set<String> collectShopIdSet;

    @Schema(description = "取消收藏门店Id集合")
    private Set<String> unCollectShopIdSet;

}
