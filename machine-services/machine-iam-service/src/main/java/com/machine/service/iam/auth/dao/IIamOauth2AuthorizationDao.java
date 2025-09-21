package com.machine.service.iam.auth.dao;

import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationEntity;

public interface IIamOauth2AuthorizationDao {


    int save(IamOauth2AuthorizationEntity entity);

    int update(IamOauth2AuthorizationEntity entity);

    IamOauth2AuthorizationEntity findById(String id);

    IamOauth2AuthorizationEntity findByClientId(String clientId);

    void remove(String id);

    IamOauth2AuthorizationEntity findByToken(IamOauth2AuthorizationEntity entity);
}
