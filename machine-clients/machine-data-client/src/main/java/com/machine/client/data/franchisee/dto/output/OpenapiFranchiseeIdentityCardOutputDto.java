package com.machine.client.data.franchisee.dto.output;

import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenapiFranchiseeIdentityCardOutputDto {
    @Schema(description = "身份证（临时:未审核）")
    private IdentityCardDto temporaryIdentityCard;

    @Schema(description = "身份证（永久:已审核）")
    private IdentityCardDto permanentIdentityCard;
}
