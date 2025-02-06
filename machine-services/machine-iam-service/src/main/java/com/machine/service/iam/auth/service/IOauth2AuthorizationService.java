package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.input.OAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.OAuth2AuthorizationOutputDto;

public interface IOauth2AuthorizationService {

    int save(OAuth2AuthorizationDto dto);

    int update(OAuth2AuthorizationDto dto);

    OAuth2AuthorizationOutputDto findById(String id);

    OAuth2AuthorizationOutputDto findByToken(OAuth2AuthorizationDto dto);

    void remove(String id);
}
