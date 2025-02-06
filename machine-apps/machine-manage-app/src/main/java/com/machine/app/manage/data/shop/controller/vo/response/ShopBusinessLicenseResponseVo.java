package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.CertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopBusinessLicenseResponseVo {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private CertificateStatusEnum certificateStatus;

    @Schema(description = "门店营业执照（临时:未审核）")
    private ShopBusinessLicenseDto temporaryBusinessLicense;

    @Schema(description = "门店营业执照（永久:已审核）")
    private ShopBusinessLicenseDto permanentBusinessLicense;
}
