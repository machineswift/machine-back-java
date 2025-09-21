package com.machine.app.openapi.data.franchisee.controller.vo.response;

import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiFranchiseeIdentityCardResponseVo {
    @Schema(description = "身份证（临时:未审核）")
    private IdentityCardDto temporaryIdentityCard;

    @Schema(description = "身份证（永久:已审核）")
    private IdentityCardDto permanentIdentityCard;
}
