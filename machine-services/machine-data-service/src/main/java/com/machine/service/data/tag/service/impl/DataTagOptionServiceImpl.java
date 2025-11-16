package com.machine.service.data.tag.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagOptionDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagOptionListOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.tag.dao.IDataTagDao;
import com.machine.service.data.tag.dao.IDataTagOptionDao;
import com.machine.service.data.tag.dao.mapper.entity.DataTagEntity;
import com.machine.service.data.tag.dao.mapper.entity.DataTagOptionEntity;
import com.machine.service.data.tag.service.IDataTagOptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DataTagOptionServiceImpl implements IDataTagOptionService {

    @Autowired
    private IDataTagOptionDao tagOptionDao;

    @Autowired
    private IDataTagDao tagDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataTagOptionCreateInputDto inputDto) {
        // 验证智能标签ID是否存在
        DataTagEntity tagEntity = tagDao.getById(inputDto.getTagId());
        if (tagEntity == null) {
            throw new DataBusinessException("data.tagOption.service.create.tagIdNotExists", "智能标签ID不存在");
        }

        // 验证编码是否已存在
        DataTagOptionEntity existingByCode = tagOptionDao.getByTagIdAndCode(inputDto.getTagId(), inputDto.getCode());
        if (existingByCode != null) {
            throw new DataBusinessException("data.tagOption.service.create.codeAlreadyExists", "编码已存在");
        }

        // 验证名称是否已存在
        DataTagOptionEntity existingByName = tagOptionDao.getByTagIdAndName(inputDto.getTagId(), inputDto.getName());
        if (existingByName != null) {
            throw new DataBusinessException("data.tagOption.service.create.nameAlreadyExists", "名称已存在");
        }

        DataTagOptionEntity entity = new DataTagOptionEntity();
        entity.setTagId(inputDto.getTagId());
        entity.setCode(inputDto.getCode());
        entity.setName(inputDto.getName());
        entity.setSort(inputDto.getSort());
        entity.setDescription(inputDto.getDescription());
        entity.setStatus(StatusEnum.DISABLE);

        return tagOptionDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataTagOptionEntity entity = tagOptionDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }

        return tagOptionDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataTagOptionUpdateInputDto inputDto) {
        DataTagOptionEntity dbEntity = tagOptionDao.getById(inputDto.getId());
        if (dbEntity == null) {
            throw new DataBusinessException("data.tagOption.service.update.idNotExists", "智能标签选项ID不存在");
        }

        // 验证名称是否已存在（排除自己）
        DataTagOptionEntity existingByName = tagOptionDao.getByTagIdAndName(dbEntity.getTagId(), inputDto.getName());
        if (existingByName != null && !existingByName.getId().equals(inputDto.getId())) {
            throw new DataBusinessException("data.tagOption.service.update.nameAlreadyExists", "名称已存在");
        }

        DataTagOptionEntity entity = new DataTagOptionEntity();
        entity.setId(inputDto.getId());
        entity.setName(inputDto.getName());
        entity.setDescription(inputDto.getDescription());

        return tagOptionDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCode(DataTagOptionUpdateCodeInputDto inputDto) {
        DataTagOptionEntity dbEntity = tagOptionDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getCode().equals(inputDto.getCode())) {
            //相同直接返回
            return 0;
        }

        // 验证编码是否已存在
        DataTagOptionEntity entityByCode = tagOptionDao.getByTagIdAndCode(dbEntity.getTagId(), inputDto.getCode());
        if (entityByCode != null && !entityByCode.getId().equals(inputDto.getId())) {
            throw new DataBusinessException("data.tagOption.service.updateCode.codeAlreadyExists", "标签选项编码已存在");
        }

        DataTagOptionEntity entity = new DataTagOptionEntity();
        entity.setId(inputDto.getId());
        entity.setCode(inputDto.getCode());
        return tagOptionDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataTagOptionUpdateStatusInputDto inputDto) {
        DataTagOptionEntity dbEntity = tagOptionDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getStatus().equals(inputDto.getStatus())) {
            //相同直接返回
            return 0;
        }

        DataTagOptionEntity entity = new DataTagOptionEntity();
        entity.setId(inputDto.getId());
        entity.setStatus(inputDto.getStatus());
        return tagOptionDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSort(DataTagOptionUpdateSortInputDto inputDto) {
        DataTagOptionEntity dbEntity = tagOptionDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getSort().equals(inputDto.getSort())) {
            //相同直接返回
            return 0;
        }

        DataTagOptionEntity entity = new DataTagOptionEntity();
        entity.setId(inputDto.getId());
        entity.setSort(inputDto.getSort());
        return tagOptionDao.update(entity);
    }

    @Override
    public DataTagOptionDetailOutputDto detail(IdRequest request) {
        DataTagOptionEntity entity = tagOptionDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataTagOptionDetailOutputDto.class);
    }

    @Override
    public List<DataTagOptionListOutputDto> selectList(DataTagOptionQueryListInputDto inputDto) {
        List<DataTagOptionEntity> entityList = tagOptionDao.selectByTagId(inputDto.getTagId());
        if (CollectionUtils.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataTagOptionListOutputDto.class);
    }

}
