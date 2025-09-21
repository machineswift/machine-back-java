package com.machine.starter.security.config.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.IIamOauth2AuthorizationClient;
import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;
import com.machine.sdk.common.context.AppContext;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

public class CustomerOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final IIamOauth2AuthorizationClient oauth2AuthorizationClient;

    public CustomerOAuth2AuthorizationService(IIamOauth2AuthorizationClient oauth2AuthorizationClient) {
        this.oauth2AuthorizationClient = oauth2AuthorizationClient;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        Assert.notNull(authorization, "authorization cannot be null");
        OAuth2Authorization existingAuthorization = findById(authorization.getId());
        if (existingAuthorization == null) {
            insertAuthorization(authorization);
        } else {
            updateAuthorization(authorization);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        String id = authorization.getId();
        oauth2AuthorizationClient.remove(id);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2AuthorizationOutputDto dto = oauth2AuthorizationClient.findById(id);
        if (Objects.isNull(dto)) {
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(dto), OAuth2Authorization.class);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOAuth2AuthorizationDto iamOAuth2AuthorizationDto = new IamOAuth2AuthorizationDto();
        Assert.hasText(token, "token cannot be empty");
        if (tokenType == null) {
            iamOAuth2AuthorizationDto.setAuthorizationCodeValue(token);
            iamOAuth2AuthorizationDto.setAccessTokenValue(token);
            iamOAuth2AuthorizationDto.setOidcIdTokenValue(token);
            iamOAuth2AuthorizationDto.setRefreshTokenValue(token);
            iamOAuth2AuthorizationDto.setUserCodeValue(token);
            iamOAuth2AuthorizationDto.setDeviceCodeValue(token);

        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            iamOAuth2AuthorizationDto.setState(token);

        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            iamOAuth2AuthorizationDto.setAuthorizationCodeValue(token);

        } else if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
            iamOAuth2AuthorizationDto.setAccessTokenValue(token);

        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            iamOAuth2AuthorizationDto.setOidcIdTokenValue(token);
        } else if (OAuth2TokenType.REFRESH_TOKEN.equals(tokenType)) {

            iamOAuth2AuthorizationDto.setRefreshTokenValue(token);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            iamOAuth2AuthorizationDto.setUserCodeValue(token);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            iamOAuth2AuthorizationDto.setDeviceCodeValue(token);
        }
        IamOAuth2AuthorizationOutputDto dto = oauth2AuthorizationClient.findByToken(iamOAuth2AuthorizationDto);
        if (Objects.isNull(dto)) {
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(dto), OAuth2Authorization.class);
    }

    private void updateAuthorization(OAuth2Authorization authorization) {
        IamOAuth2AuthorizationDto dto = new IamOAuth2AuthorizationDto();
        BeanUtil.copyProperties(authorization, dto);
        oauth2AuthorizationClient.update(dto);
    }

    private void insertAuthorization(OAuth2Authorization authorization) {
        IamOAuth2AuthorizationDto dto = new IamOAuth2AuthorizationDto();
        BeanUtil.copyProperties(authorization, dto);
        dto.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        dto.setAuthorizedScopes(String.join(",", authorization.getAuthorizedScopes()));
        oauth2AuthorizationClient.save(dto);
    }
}
