package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.IamOauth2AuthorizationConsentOutputDto;

public interface IIamOauth2AuthorizationConsentService {

    int add(IamAuthTokenAddDto dto);

    void update(IamOauth2AuthorizationConsentInputDto dto);

    void save(IamOauth2AuthorizationConsentInputDto dto);

    void remove(IamOauth2AuthorizationConsentInputDto dto);

    IamOauth2AuthorizationConsentOutputDto findById(IamOauth2AuthorizationConsentInputDto dto);
}
