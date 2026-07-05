package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.dto.input.ScmPropertyValueCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.IScmPropertyDao;
import com.machine.service.scm.property.dao.IScmPropertyValueDao;
import com.machine.service.scm.property.dao.IScmPropertyValueRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueEntity;
import com.machine.service.scm.property.service.IScmPropertyValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmPropertyValueServiceImpl implements IScmPropertyValueService {

    @Autowired
    private IScmPropertyDao propertyDao;

    @Autowired
    private IScmPropertyValueDao propertyValueDao;

    @Autowired
    private IScmPropertyValueRelationDao propertyValueRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmPropertyValueCreateInputDto inputDto) {
        validatePropertyExists(inputDto.getPropertyId());
        if (propertyValueDao.countByPropertyIdAndValue(inputDto.getPropertyId(), inputDto.getValue()) > 0) {
            throw new ScmBusinessException("scm.propertyValue.service.create.valueAlreadyExists", "属性值已经存在");
        }
        ScmPropertyValueEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyValueEntity.class);
        return propertyValueDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmPropertyValueUpdateInputDto inputDto) {
        ScmPropertyValueEntity entityById = propertyValueDao.getById(inputDto.getId());
        if (entityById == null) {
            throw new ScmBusinessException("scm.propertyValue.service.update.notExists", "属性值不存在");
        }
        if (propertyValueDao.countByPropertyIdAndValueExcludeId(
                entityById.getPropertyId(), inputDto.getValue(), inputDto.getId()) > 0) {
            throw new ScmBusinessException("scm.propertyValue.service.update.valueAlreadyExists", "属性值已经存在");
        }
        ScmPropertyValueEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyValueEntity.class);
        entity.setPropertyId(entityById.getPropertyId());
        return propertyValueDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        ScmPropertyValueEntity entity = propertyValueDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }
        propertyValueRelationDao.deleteByParentValueId(request.getId());
        return propertyValueDao.deleteById(request.getId());
    }

    @Override
    public ScmPropertyValueDetailOutputDto getById(IdRequest request) {
        ScmPropertyValueEntity entity = propertyValueDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyValueDetailOutputDto.class);
    }

    @Override
    public List<ScmPropertyValueListOutputDto> listByPropertyId(ScmPropertyValueQueryInputDto inputDto) {
        return propertyValueDao.listByPropertyId(inputDto.getPropertyId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyValueListOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmPropertyValueEntity> listByPropertyIdSet(Set<String> propertyIdSet) {
        return propertyValueDao.listByPropertyIdSet(propertyIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPropertyId(String propertyId) {
        return propertyValueDao.deleteByPropertyId(propertyId);
    }

    @Override
    public int countByPropertyIdAndValue(String propertyId, String value) {
        return propertyValueDao.countByPropertyIdAndValue(propertyId, value);
    }

    private void validatePropertyExists(String propertyId) {
        ScmPropertyEntity property = propertyDao.getById(propertyId);
        if (property == null) {
            throw new ScmBusinessException("scm.propertyValue.service.propertyNotExists", "属性不存在");
        }
    }
}
