package com.machine.service.iam.biam.auth.dao;

import com.machine.client.iam.biam.auth.dto.input.BIamOauth2AuthorizationConsentInputDto;
import com.machine.service.iam.biam.auth.dao.mapper.entity.BIamOauth2AuthorizationConsentEntity;

public interface IBIamOauth2AuthorizationConsentDao {

    void update(BIamOauth2AuthorizationConsentEntity dto);

    void save(BIamOauth2AuthorizationConsentEntity dto);

    void remove(BIamOauth2AuthorizationConsentEntity dto);

    BIamOauth2AuthorizationConsentEntity findById(BIamOauth2AuthorizationConsentInputDto dto);
}
