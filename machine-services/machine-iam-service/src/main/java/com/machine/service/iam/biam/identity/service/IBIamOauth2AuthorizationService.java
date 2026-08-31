package com.machine.service.iam.biam.identity.service;

import com.machine.client.iam.biam.identity.dto.BIamOAuth2AuthorizationDto;

public interface IBIamOauth2AuthorizationService {

    int save(BIamOAuth2AuthorizationDto dto);

    void remove(String id);

    int update(BIamOAuth2AuthorizationDto dto);

    boolean existsById(String id);

    BIamOAuth2AuthorizationDto findById(String id);

    BIamOAuth2AuthorizationDto findByToken(BIamOAuth2AuthorizationDto dto);

}
