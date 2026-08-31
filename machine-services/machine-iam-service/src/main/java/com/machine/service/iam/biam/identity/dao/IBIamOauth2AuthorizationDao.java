package com.machine.service.iam.biam.identity.dao;

import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2AuthorizationEntity;

public interface IBIamOauth2AuthorizationDao {

    int save(BIamOauth2AuthorizationEntity entity);

    void remove(String id);

    int update(BIamOauth2AuthorizationEntity entity);

    boolean existsById(String id);

    BIamOauth2AuthorizationEntity findById(String id);

    BIamOauth2AuthorizationEntity findByToken(BIamOauth2AuthorizationEntity entity);

}
