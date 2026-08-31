package com.machine.service.iam.biam.identity.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2RegisteredClientEntity;

import java.util.List;

public interface IBIamOauth2RegisteredClientDao {

    String insert(BIamOauth2RegisteredClientEntity entity);

    int deleteById(String id);

    int update(BIamOauth2RegisteredClientEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    int updateClientSecret(String id,
                           String clientSecret);

    List<String> allClientId(StatusEnum status);

    BIamOauth2RegisteredClientEntity findById(String id);

    BIamOauth2RegisteredClientEntity findByClientId(String clientId);

    BIamOauth2RegisteredClientEntity findByClientName(String clientName);

    Page<BIamOauth2RegisteredClientEntity> selectPage(BIamOAuth2RegisteredClientPageQueryInputDto query);

}
