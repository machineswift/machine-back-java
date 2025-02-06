package com.machine.app.manage.data.shop.controller.vo.request;

import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopFrontPhotoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class ShopUpdateCertificateRequestVo {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    
    @Valid
    @Schema(description = "营业执照")
    private ShopBusinessLicenseDto businessLicense;

    @Valid
    @Schema(description = "食品经营许可证")
    private FoodBusinessLicenseDto foodBusinessLicense;

    @Valid
    @Schema(description = "消杀合同")
    private DisinfectingContractDto disinfectingContractDto;

    @Valid
    @Schema(description = "门头照")
    private ShopFrontPhotoDto shopFrontPhoto;
}
