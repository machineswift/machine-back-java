package com.machine.client.data.shop.dto.output;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataOpenApiShopFrontPhotoOutputDto {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "门头照（临时:未审核）")
    private DataShopFrontPhotoDto temporaryShopFrontPhoto;

    @Schema(description = "门头照（永久:已审核）")
    private DataShopFrontPhotoDto permanentShopFrontPhoto;
}
