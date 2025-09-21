package com.machine.app.manage.data.shop.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopCertificateResponseVo {

    @Schema(description = "门店ID")
    private String id;

    @Schema(description = "营业执照")
    private DataShopBusinessLicenseResponseVo businessLicense;

    @Schema(description = "食品经营许可证")
    private DataShopFoodBusinessLicenseResponseVo foodBusinessLicense;

    @Schema(description = "消杀合同")
    private DataShopDisinfectingContractResponseVo disinfectingContract;

    @Schema(description = "门头照")
    private DataShopFrontPhotoResponseVo frontPhoto;
}
