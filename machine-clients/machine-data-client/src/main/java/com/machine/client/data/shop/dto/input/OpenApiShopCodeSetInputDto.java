package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class OpenApiShopCodeSetInputDto {

    @NotEmpty(message = "门店编码集合不能为空")
    @Schema(description = "门店编码集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> codeSet;

    public OpenApiShopCodeSetInputDto(Set<String> codeSet) {
        this.codeSet = codeSet;
    }
}
