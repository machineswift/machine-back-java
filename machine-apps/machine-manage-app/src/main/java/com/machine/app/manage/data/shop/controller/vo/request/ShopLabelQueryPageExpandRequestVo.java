package com.machine.app.manage.data.shop.controller.vo.request;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShopLabelQueryPageExpandRequestVo extends PageRequest {

    @Schema(description = "名称")
    private String name;

}
