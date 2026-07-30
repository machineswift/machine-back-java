package com.machine.sdk.base.model.dto.iam.identity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;


@Data
@NoArgsConstructor
public class IamAuth2RegisteredTokenSettingDto {

    @Schema(description = "是否重用刷新令牌")
    private Boolean reuseRefreshTokens = false;

    @Schema(description = "是否将访问令牌绑定到客户端的X.509证书")
    private Boolean x509CertificateBoundAccessTokens = false;

    @Schema(description = "签名算法")
    private SignatureAlgorithm idTokenSignatureAlgorithm = SignatureAlgorithm.PS256;

    @Schema(description = "访问令牌有效期(小时)")
    private Long accessTokenTimeToLive = 2L;

    @Schema(description = "刷新令牌有效期(天)")
    private Long refreshTokenTimeToLive = 7L;

    @Schema(description = "访问令牌格式")
    private OAuth2TokenFormat accessTokenFormat = OAuth2TokenFormat.REFERENCE;

}
