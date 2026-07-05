package com.machine.service.ai.resource.model.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelListOutputDto;
import com.machine.sdk.base.exception.ai.AiBusinessException;
import com.machine.service.ai.resource.model.dao.IAiResourceModelDao;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceModelEntity;
import com.machine.service.ai.resource.model.service.IAiResourceModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
public class AiResourceModelServiceImpl implements IAiResourceModelService {

    @Autowired
    private IAiResourceModelDao aiResourceModelDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AiResourceModelCreateInputDto inputDto) {
        // 验证唯一约束(provider_id + code)
        AiResourceModelEntity dbEntity = aiResourceModelDao.getByProviderIdAndCode(
                inputDto.getProviderId(), inputDto.getCode());
        if (Objects.nonNull(dbEntity)) {
            log.error("资源中心新增模型已存在，providerId={}, code={}",
                    inputDto.getProviderId(), inputDto.getCode());
            throw new AiBusinessException("ai.resource.model.service.create.modelAlreadyExists", "资源中心模型已存在");
        }

        AiResourceModelEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), AiResourceModelEntity.class);
        return aiResourceModelDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String id) {
        return aiResourceModelDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(AiResourceModelUpdateInputDto inputDto) {
        AiResourceModelEntity dbEntity = aiResourceModelDao.getById(inputDto.getId());
        if (Objects.isNull(dbEntity)) {
            log.error("资源中心修改模型不存在，id={}", inputDto.getId());
            return 0;
        }

        // 验证唯一约束（排除自身）- providerId + code
        if (StrUtil.isNotBlank(inputDto.getCode())) {
            AiResourceModelEntity uniqueEntity = aiResourceModelDao.getByProviderIdAndCode(
                    StrUtil.isNotBlank(inputDto.getProviderId()) ? inputDto.getProviderId() : dbEntity.getProviderId(),
                    inputDto.getCode());
            if (Objects.nonNull(uniqueEntity) && !uniqueEntity.getId().equals(inputDto.getId())) {
                log.error("资源中心修改模型唯一约束冲突，code={}", inputDto.getCode());
                throw new AiBusinessException("ai.resource.model.service.update.modelAlreadyExists", "资源中心模型已存在");
            }
        }

        AiResourceModelEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), AiResourceModelEntity.class);
        return aiResourceModelDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(AiResourceModelUpdateStatusInputDto inputDto) {
        return aiResourceModelDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    public AiResourceModelDetailOutputDto detail(String id) {
        AiResourceModelEntity entity = aiResourceModelDao.getById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), AiResourceModelDetailOutputDto.class);
    }

    @Override
    public Page<AiResourceModelListOutputDto> selectPage(AiResourceModelQueryPageInputDto inputDto) {
        Page<AiResourceModelEntity> page = aiResourceModelDao.selectPage(inputDto);
        Page<AiResourceModelListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), AiResourceModelListOutputDto.class));
        return pageResult;
    }
}
