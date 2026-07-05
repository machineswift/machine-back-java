package com.machine.service.ai.resource.model.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceModelEntity;

public interface IAiResourceModelDao {

    String insert(AiResourceModelEntity entity);

    int deleteById(String id);

    int update(AiResourceModelEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    AiResourceModelEntity getById(String id);

    AiResourceModelEntity getByProviderIdAndCode(String providerId,
                                                 String code);

    Page<AiResourceModelEntity> selectPage(AiResourceModelQueryPageInputDto inputDto);
}
