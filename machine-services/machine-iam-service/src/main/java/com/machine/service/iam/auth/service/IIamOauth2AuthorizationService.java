package com.machine.service.iam.auth.service;

import com.machine.client.iam.auth.dto.input.IamOAuth2AuthorizationDto;
import com.machine.client.iam.auth.dto.output.IamOAuth2AuthorizationOutputDto;

public interface IIamOauth2AuthorizationService {

    int save(IamOAuth2AuthorizationDto dto);

    int update(IamOAuth2AuthorizationDto dto);

    IamOAuth2AuthorizationOutputDto findById(String id);

    IamOAuth2AuthorizationOutputDto findByToken(IamOAuth2AuthorizationDto dto);

    void remove(String id);
}
