package com.machine.client.data.employee.dto.output;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiShopEmployeeIdentityCardOutputDto {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "身份证（临时:未审核）")
    private IdentityCardDto temporaryIdentityCard;

    @Schema(description = "身份证（永久:已审核）")
    private IdentityCardDto permanentIdentityCard;
}
