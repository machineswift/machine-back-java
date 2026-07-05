package com.machine.service.ai.resource.model.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderListOutputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.exception.ai.AiBusinessException;
import com.machine.service.ai.resource.model.dao.IAiResourceProviderDao;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceProviderEntity;
import com.machine.service.ai.resource.model.service.IAiResourceProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AiResourceProviderServiceImpl implements IAiResourceProviderService {

    @Autowired
    private IAiResourceProviderDao aiResourceProviderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AiResourceProviderCreateInputDto inputDto) {
        // 验证唯一约束
        AiResourceProviderEntity dbEntity = aiResourceProviderDao.getByProvider(inputDto.getProvider());
        if (Objects.nonNull(dbEntity)) {
            log.error("资源中心新增厂商已存在，provider={}", inputDto.getProvider());
            throw new AiBusinessException("ai.resource.provider.service.create.providerAlreadyExists", "资源中心厂商已存在");
        }

        AiResourceProviderEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), AiResourceProviderEntity.class);
        return aiResourceProviderDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String id) {
        return aiResourceProviderDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(AiResourceProviderUpdateInputDto inputDto) {
        AiResourceProviderEntity dbEntity = aiResourceProviderDao.getById(inputDto.getId());
        if (Objects.isNull(dbEntity)) {
            log.error("资源中心修改厂商不存在，id={}", inputDto.getId());
            return 0;
        }

        if (StrUtil.isBlank(inputDto.getApiKey())) {
            inputDto.setApiKey(null);
        }

        AiResourceProviderEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), AiResourceProviderEntity.class);
        return aiResourceProviderDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(AiResourceProviderUpdateStatusInputDto inputDto) {
        return aiResourceProviderDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    public AiResourceProviderDetailOutputDto detail(String id) {
        AiResourceProviderEntity entity = aiResourceProviderDao.getById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), AiResourceProviderDetailOutputDto.class);
    }

    @Override
    public List<AiResourceProviderListOutputDto> list(StatusEnum status) {
        List<AiResourceProviderEntity> entityList = aiResourceProviderDao.list(status);
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), AiResourceProviderListOutputDto.class);
    }
}
