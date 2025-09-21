package com.machine.app.manage.data.shop.controller.vo.request;

import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopUpdateCertificateRequestVo {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    
    @Valid
    @Schema(description = "营业执照")
    private DataShopBusinessLicenseDto businessLicense;

    @Valid
    @Schema(description = "食品经营许可证")
    private DataShopFoodBusinessLicenseDto foodBusinessLicense;

    @Valid
    @Schema(description = "消杀合同")
    private DataShopDisinfectingContractDto disinfectingContractDto;

    @Valid
    @Schema(description = "门头照")
    private DataShopFrontPhotoDto shopFrontPhoto;
}
