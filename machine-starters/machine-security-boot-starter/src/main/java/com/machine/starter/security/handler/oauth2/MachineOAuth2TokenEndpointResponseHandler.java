package com.machine.starter.security.handler.oauth2;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.model.AppResult;
import com.machine.starter.security.service.model.MachineAuthenticationResult;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 令牌端点（/token）认证成功响应处理器
 * <p>
 * 将 Spring Authorization Server 默认的 {@code {access_token, token_type, expires_in, ...}} 响应
 * 统一包装为项目标准的 {@link AppResult} 结构。
 */
@Component
public class MachineOAuth2TokenEndpointResponseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull Authentication authentication) throws IOException {
        OAuth2AccessTokenAuthenticationToken accessTokenAuthentication =
                (OAuth2AccessTokenAuthenticationToken) authentication;

        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();

//        if (accessToken.getScopes() != null && !accessToken.getScopes().isEmpty()) {
//            data.put("scope", String.join(" ", accessToken.getScopes()));
//        }
//        // OIDC id_token 等附加参数
//        data.putAll(accessTokenAuthentication.getAdditionalParameters());

        MachineAuthenticationResult authenticationResult = new MachineAuthenticationResult();
        authenticationResult.setAccessToken(accessToken.getTokenValue());
        if (null != accessToken.getExpiresAt()) {
            authenticationResult.setExpiresIn(accessToken.getExpiresAt().toEpochMilli());
        }
        if (refreshToken != null) {
            authenticationResult.setRefreshToken(refreshToken.getTokenValue());
        }
        authenticationResult.setTokenType(accessToken.getTokenType().getValue());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        AppResult<MachineAuthenticationResult> appResult = AppResult.success(authenticationResult);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(appResult).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
