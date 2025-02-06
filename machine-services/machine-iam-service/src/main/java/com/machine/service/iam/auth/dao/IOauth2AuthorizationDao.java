package com.machine.service.iam.auth.dao;

import com.machine.client.iam.auth.dto.input.OAuth2AuthorizationDto;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationEntity;

public interface IOauth2AuthorizationDao {


    int save(Oauth2AuthorizationEntity entity);

    int update(Oauth2AuthorizationEntity entity);

    Oauth2AuthorizationEntity findById(String id);

    Oauth2AuthorizationEntity findByClientId(String clientId);

    void remove(String id);

    Oauth2AuthorizationEntity findByToken(Oauth2AuthorizationEntity entity);
}
