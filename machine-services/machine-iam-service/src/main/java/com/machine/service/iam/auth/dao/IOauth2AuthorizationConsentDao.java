package com.machine.service.iam.auth.dao;

import com.machine.client.iam.auth.dto.input.Oauth2AuthorizationConsentInputDto;
import com.machine.client.iam.auth.dto.output.Oauth2AuthorizationConsentOutputDto;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationConsentEntity;

public interface IOauth2AuthorizationConsentDao {

    void update(Oauth2AuthorizationConsentEntity dto);

    void save(Oauth2AuthorizationConsentEntity dto);

    void remove(Oauth2AuthorizationConsentEntity dto);

    Oauth2AuthorizationConsentEntity findById(Oauth2AuthorizationConsentInputDto dto);
}
