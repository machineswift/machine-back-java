package com.machine.sdk.base.model.dto.iam.identity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamAuth2RegisteredClientSettingDto {

    @Schema(description = "强制PKCE")
    private Boolean requireProofKey = false;

    @Schema(description = "需要用户确")
    private Boolean requireAuthorizationConsent = false;

}
