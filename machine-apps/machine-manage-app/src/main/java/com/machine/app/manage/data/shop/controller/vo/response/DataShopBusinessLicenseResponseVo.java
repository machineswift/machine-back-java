package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopBusinessLicenseResponseVo {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "门店营业执照（临时:未审核）")
    private DataShopBusinessLicenseDto temporaryBusinessLicense;

    @Schema(description = "门店营业执照（永久:已审核）")
    private DataShopBusinessLicenseDto permanentBusinessLicense;
}
