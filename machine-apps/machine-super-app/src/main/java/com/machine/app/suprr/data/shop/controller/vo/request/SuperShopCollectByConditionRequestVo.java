package com.machine.app.suprr.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@Schema
public class SuperShopCollectByConditionRequestVo {

    @Schema(description = "是否收藏（true:收藏、false:取消收藏）")
    @NotNull(message = "是否收藏不能为空")
    private Boolean collected;

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

    @Schema(description = "关键字（名称/编码）")
    private String keyWord;

    @Schema(description = "组织Id集合")
    private Set<String> organizationIdSet;

    @Schema(description = "区域编码集合")
    private Set<String> areaCodeSet;

}
