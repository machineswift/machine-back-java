package com.machine.service.ai.resource.model.dao;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.ai.AiProviderEnum;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceProviderEntity;

import java.util.List;

public interface IAiResourceProviderDao {

    String insert(AiResourceProviderEntity entity);

    int deleteById(String id);

    int update(AiResourceProviderEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    AiResourceProviderEntity getById(String id);

    AiResourceProviderEntity getByProvider(AiProviderEnum provider);

    List<AiResourceProviderEntity> list(StatusEnum status);
}
