package com.machine.starter.security.config.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * JWT(SELF_CONTAINED) AccessToken 的 Claims 自定义器
 */
@Slf4j
public class MachineOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {


    public MachineOAuth2TokenCustomizer() {
    }

    @Override
    public void customize(JwtEncodingContext context) {
        String grantType = context.getAuthorizationGrantType().getValue();
        context.getClaims().claim("grantType", grantType);
    }

}
