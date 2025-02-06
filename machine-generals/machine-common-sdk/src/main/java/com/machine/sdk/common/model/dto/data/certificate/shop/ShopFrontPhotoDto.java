package com.machine.sdk.common.model.dto.data.certificate.shop;

import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 门店门头照
 */
@Data
@Schema
@NoArgsConstructor
@Validated
public class ShopFrontPhotoDto {

    @NotNull(message = "商户门头照不能为空")
    @Schema(description = "商户门头照")
    private List<MaterialDto> storeFrontPhotoList;

    @NotNull(message = "前台照不能为空")
    @Schema(description = "前台照")
    private List<MaterialDto> frontDeskPhotolList;

}
