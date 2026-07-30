package com.machine.service.iam.identity.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.dto.input.IamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.identity.dao.mapper.entiry.IamOauth2RegisteredClientEntity;

import java.util.List;

public interface IIamOauth2RegisteredClientDao {

    String insert(IamOauth2RegisteredClientEntity entity);

    int update(IamOauth2RegisteredClientEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    int deleteById(String id);

    List<String> allClientId(StatusEnum status);

    IamOauth2RegisteredClientEntity findById(String id);

    IamOauth2RegisteredClientEntity findByClientId(String clientId);

    IamOauth2RegisteredClientEntity findByClientName(String clientName);

    Page<IamOauth2RegisteredClientEntity> selectPage(IamOAuth2RegisteredClientPageQueryInputDto query);

}
