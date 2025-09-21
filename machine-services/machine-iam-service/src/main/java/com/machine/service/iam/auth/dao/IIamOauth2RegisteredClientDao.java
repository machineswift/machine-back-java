package com.machine.service.iam.auth.dao;

import com.machine.client.iam.auth.dto.IamAuthTokenAddDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2RegisteredClientEntity;

import java.util.List;

public interface IIamOauth2RegisteredClientDao {

    int add(IamAuthTokenAddDto dto);

    int save(IamOauth2RegisteredClientEntity entity);

    int update(IamOauth2RegisteredClientEntity entity);

    List<String> allClientId(StatusEnum status);

    IamOauth2RegisteredClientEntity findById(String id);

    IamOauth2RegisteredClientEntity findByClientId(String clientId);

}
