package com.machine.service.iam.biam.auth.service;

import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.biam.auth.dto.output.BIamOauth2AuthorizationConsentOutputDto;

public interface IBIamOauth2AuthorizationConsentService {

    void update(BIamOauth2AuthorizationConsentInputDto dto);

    void save(BIamOauth2AuthorizationConsentInputDto dto);

    void remove(BIamOauth2AuthorizationConsentInputDto dto);

    BIamOauth2AuthorizationConsentOutputDto findById(BIamOauth2AuthorizationConsentInputDto dto);
}
