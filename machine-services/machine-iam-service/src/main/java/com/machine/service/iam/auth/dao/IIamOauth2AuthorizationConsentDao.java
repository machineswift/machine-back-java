package com.machine.service.iam.auth.dao;

import com.machine.client.iam.auth.dto.input.IamOauth2AuthorizationConsentInputDto;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationConsentEntity;

public interface IIamOauth2AuthorizationConsentDao {

    void update(IamOauth2AuthorizationConsentEntity dto);

    void save(IamOauth2AuthorizationConsentEntity dto);

    void remove(IamOauth2AuthorizationConsentEntity dto);

    IamOauth2AuthorizationConsentEntity findById(IamOauth2AuthorizationConsentInputDto dto);
}
