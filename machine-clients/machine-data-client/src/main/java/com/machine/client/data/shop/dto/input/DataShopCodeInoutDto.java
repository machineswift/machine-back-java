package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopCodeInoutDto {

    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码")
    private String code;

    public DataShopCodeInoutDto(String code) {
        this.code = code;
    }
}
