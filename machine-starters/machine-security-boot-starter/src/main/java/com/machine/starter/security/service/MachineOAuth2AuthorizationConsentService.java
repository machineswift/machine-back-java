package com.machine.starter.security.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.auth.IBIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.biam.auth.dto.output.BIamOauth2AuthorizationConsentOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

public class MachineOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final IBIamOauth2AuthorizationConsentClient authorizationConsentClient;

    public MachineOAuth2AuthorizationConsentService(IBIamOauth2AuthorizationConsentClient authorizationConsentClient) {
        this.authorizationConsentClient = authorizationConsentClient;
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        OAuth2AuthorizationConsent existing = findById(authorizationConsent.getRegisteredClientId(),
                authorizationConsent.getPrincipalName());
        if (existing == null) {
            insertAuthorizationConsent(authorizationConsent);
        } else {
            updateAuthorizationConsent(authorizationConsent);
        }
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOauth2AuthorizationConsentInputDto dto = new BIamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.remove(dto);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);
        BIamOauth2AuthorizationConsentInputDto dto = new BIamOauth2AuthorizationConsentInputDto();
        dto.setRegisteredClientId(registeredClientId);
        dto.setPrincipalName(principalName);
        BIamOauth2AuthorizationConsentOutputDto inputDto = authorizationConsentClient.findById(dto);
        if (Objects.isNull(inputDto)) {
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(inputDto), OAuth2AuthorizationConsent.class);
    }

    private void updateAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        BIamOauth2AuthorizationConsentInputDto dto = new BIamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.update(dto);
    }

    private void insertAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        BIamOauth2AuthorizationConsentInputDto dto = new BIamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.save(dto);
    }
}

