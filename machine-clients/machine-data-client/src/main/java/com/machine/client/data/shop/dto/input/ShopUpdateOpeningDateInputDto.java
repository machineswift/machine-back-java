package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Schema
public class ShopUpdateOpeningDateInputDto {

    @NotBlank(message = "门店编码不能为空")
    @Schema(description = "门店编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotNull(message = "开业日期不能为空")
    @Schema(description = "开业日期")
    private Long openingDate;

}
