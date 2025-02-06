package com.machine.service.iam.auth.dao;

import com.machine.client.iam.auth.dto.AuthTokenAddDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2RegisteredClientEntity;

import java.util.List;

public interface IOauth2RegisteredClientDao {

    int add(AuthTokenAddDto dto);

    int save(Oauth2RegisteredClientEntity entity);

    int update(Oauth2RegisteredClientEntity entity);

    List<String> allClientId(StatusEnum status);

    Oauth2RegisteredClientEntity findById(String id);

    Oauth2RegisteredClientEntity findByClientId(String clientId);

}
