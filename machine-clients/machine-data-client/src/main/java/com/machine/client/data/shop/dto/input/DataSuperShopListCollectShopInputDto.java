package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class DataSuperShopListCollectShopInputDto extends PageRequest {

    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "关键字（名称/编码）")
    private String keyword;

    @Schema(description = "门店Id集合")
    private Set<String> shopIdSet;
}
