package com.machine.starter.security.config.db;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.auth.IIamOauth2AuthorizationConsentClient;
import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.IamOauth2AuthorizationConsentOutputDto;
import com.machine.sdk.common.context.AppContext;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

public class CustomerOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final IIamOauth2AuthorizationConsentClient authorizationConsentClient;

    public CustomerOAuth2AuthorizationConsentService(IIamOauth2AuthorizationConsentClient authorizationConsentClient) {
        this.authorizationConsentClient = authorizationConsentClient;
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
        OAuth2AuthorizationConsent existingAuthorizationConsent = findById(authorizationConsent.getRegisteredClientId(),
                authorizationConsent.getPrincipalName());
        if (existingAuthorizationConsent == null) {
            insertAuthorizationConsent(authorizationConsent);
        } else {
            updateAuthorizationConsent(authorizationConsent);
        }
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOauth2AuthorizationConsentInputDto dto = new IamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.remove(dto);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        IamOauth2AuthorizationConsentInputDto dto = new IamOauth2AuthorizationConsentInputDto();
        dto.setRegisteredClientId(registeredClientId);
        dto.setPrincipalName(principalName);
        IamOauth2AuthorizationConsentOutputDto inputDto = authorizationConsentClient.findById(dto);
        if (Objects.isNull(inputDto)) {
            return null;
        }
        return BeanUtil.toBean(JSONUtil.toJsonStr(inputDto), OAuth2AuthorizationConsent.class);
    }

    private void updateAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        IamOauth2AuthorizationConsentInputDto dto = new IamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.update(dto);
    }

    private void insertAuthorizationConsent(OAuth2AuthorizationConsent authorizationConsent) {
        IamOauth2AuthorizationConsentInputDto dto = new IamOauth2AuthorizationConsentInputDto();
        BeanUtil.copyProperties(authorizationConsent, dto);
        authorizationConsentClient.save(dto);
    }
}

