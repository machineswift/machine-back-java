package com.machine.app.suprr.data.shop.controller.vo.request;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperShopListCollectShopRequestVo extends PageRequest {

    @Schema(description = "关键字（名称/编码）")
    private String keyword;

}
