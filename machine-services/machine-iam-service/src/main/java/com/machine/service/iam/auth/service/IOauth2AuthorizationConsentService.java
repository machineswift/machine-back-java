package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;

public interface IOauth2AuthorizationConsentService {

    int add(AuthTokenAddDto dto);

    void update(Oauth2AuthorizationConsentInputDto dto);

    void save(Oauth2AuthorizationConsentInputDto dto);

    void remove(Oauth2AuthorizationConsentInputDto dto);

    Oauth2AuthorizationConsentOutputDto findById(Oauth2AuthorizationConsentInputDto dto);
}
