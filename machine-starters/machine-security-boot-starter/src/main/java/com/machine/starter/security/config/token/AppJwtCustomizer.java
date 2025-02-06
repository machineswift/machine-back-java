package com.machine.starter.security.config.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Slf4j
public class AppJwtCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Override
    public void customize(JwtEncodingContext context) {
        String grantType = context.getAuthorizationGrantType().getValue();
        context.getClaims().claim("grantType", grantType);

        if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.REFRESH_TOKEN)) {
            context.getClaims().claim("grantType", grantType);
        } else if (context.getAuthorizationGrantType().equals(AuthorizationGrantType.CLIENT_CREDENTIALS)) {
            context.getClaims().claim("grantType", grantType);
        }
    }
}
