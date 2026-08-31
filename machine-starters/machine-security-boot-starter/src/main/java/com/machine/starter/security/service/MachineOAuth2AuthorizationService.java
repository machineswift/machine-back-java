package com.machine.starter.security.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.identity.IBIamOauth2AuthorizationClient;
import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import com.machine.starter.security.service.convert.OAuth2Authorization2DtoConverter;
import com.machine.starter.security.service.convert.OAuth2Dto2AuthorizationConverter;
import com.machine.starter.security.service.convert.OAuth2Dto2RegisteredClientConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Objects;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

public class MachineOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final IBIamOauth2AuthorizationClient oauth2AuthorizationClient;
    private final CaffeineCacheRegisteredClient caffeineCacheRegisteredClient;

    public MachineOAuth2AuthorizationService(IBIamOauth2AuthorizationClient oauth2AuthorizationClient,
                                             CaffeineCacheRegisteredClient caffeineCacheRegisteredClient) {
        this.oauth2AuthorizationClient = oauth2AuthorizationClient;
        this.caffeineCacheRegisteredClient = caffeineCacheRegisteredClient;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOAuth2AuthorizationDto authorizationDto = OAuth2Authorization2DtoConverter.toDto(authorization);
        if (oauth2AuthorizationClient.existsById(authorization.getId())) {
            oauth2AuthorizationClient.update(authorizationDto);
        } else {
            oauth2AuthorizationClient.save(authorizationDto);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        String id = authorization.getId();
        oauth2AuthorizationClient.remove(id);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOAuth2AuthorizationDto authorizationDto = oauth2AuthorizationClient.findById(id);
        if (Objects.isNull(authorizationDto)) {
            return null;
        }

        BIamOAuth2RegisteredClientDto registeredClientDto = caffeineCacheRegisteredClient.getByClientId(authorizationDto.getRegisteredClientId());
        RegisteredClient registeredClient = OAuth2Dto2RegisteredClientConverter.convert2Client(registeredClientDto);
        return OAuth2Dto2AuthorizationConverter.toEntity(authorizationDto, registeredClient);
    }

    @Override
    public OAuth2Authorization findByToken(String token,
                                           OAuth2TokenType tokenType) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOAuth2AuthorizationDto iamOAuth2AuthorizationDto = new BIamOAuth2AuthorizationDto();

        if (tokenType == null) {
            throw new BIamBusinessException("iam.identity.repository.findByToken.nullTokenType", "token类型为空");
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

        BIamOAuth2AuthorizationDto dto = oauth2AuthorizationClient.findByToken(iamOAuth2AuthorizationDto);
        if (Objects.isNull(dto)) {
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(dto), OAuth2Authorization.class);
    }

}
