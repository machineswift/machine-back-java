package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopCreateInputDto {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Valid
    @NotNull(message = "地址信息不能为空")
    @Schema(description = "地址信息")
    private AddressInfoDto addressInfo;

    @Schema(description = "经度")
    private Long latitude;

    @Schema(description = "纬度")
    private Long longitude;

    @Schema(description = "描述")
    private String description;

}
