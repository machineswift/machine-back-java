package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperShopListCollectShopInputDto extends PageRequest {

    @Schema(description = "用户Id--（不需要传，本地线程获取）")
    private String userId;

    @Schema(description = "店铺名称")
    private String name;

    @Schema(description = "店铺Id集合")
    private Set<String> shopIdSet;
}
