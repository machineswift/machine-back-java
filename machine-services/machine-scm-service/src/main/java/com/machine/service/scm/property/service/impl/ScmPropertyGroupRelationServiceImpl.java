package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupRelationDetailOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.IScmPropertyDao;
import com.machine.service.scm.property.dao.IScmPropertyGroupDao;
import com.machine.service.scm.property.dao.IScmPropertyGroupRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupEntity;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupRelationEntity;
import com.machine.service.scm.property.service.IScmPropertyGroupRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScmPropertyGroupRelationServiceImpl implements IScmPropertyGroupRelationService {

    @Autowired
    private IScmPropertyGroupRelationDao relationDao;

    @Autowired
    private IScmPropertyGroupDao propertyGroupDao;

    @Autowired
    private IScmPropertyDao propertyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmPropertyGroupRelationCreateInputDto inputDto) {
        validateGroupAndProperty(inputDto.getGroupId(), inputDto.getPropertyId());
        if (relationDao.countByGroupAndProperty(inputDto.getGroupId(), inputDto.getPropertyId()) > 0) {
            throw new ScmBusinessException("scm.propertyGroupRelation.service.create.alreadyExists", "组内已存在该属性");
        }
        ScmPropertyGroupRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyGroupRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmPropertyGroupRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    public ScmPropertyGroupRelationDetailOutputDto getById(IdRequest request) {
        ScmPropertyGroupRelationEntity entity = relationDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyGroupRelationDetailOutputDto.class);
    }

    @Override
    public List<String> listPropertyIdByGroupId(IdRequest request) {
        return relationDao.listPropertyIdByGroupId(request.getId());
    }

    @Override
    public List<ScmPropertyGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet) {
        return relationDao.listByGroupIdSet(groupIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByGroupId(String groupId) {
        return relationDao.deleteByGroupId(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPropertyId(String propertyId) {
        return relationDao.deleteByPropertyId(propertyId);
    }

    private void validateGroupAndProperty(String groupId, String propertyId) {
        ScmPropertyGroupEntity group = propertyGroupDao.getById(groupId);
        if (group == null) {
            throw new ScmBusinessException("scm.propertyGroupRelation.service.groupNotExists", "属性分组不存在");
        }
        ScmPropertyEntity property = propertyDao.getById(propertyId);
        if (property == null) {
            throw new ScmBusinessException("scm.propertyGroupRelation.service.propertyNotExists", "属性不存在");
        }
    }
}
