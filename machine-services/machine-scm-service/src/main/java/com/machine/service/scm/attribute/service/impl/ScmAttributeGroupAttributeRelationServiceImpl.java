package com.machine.service.scm.attribute.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupDao;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupAttributeRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;
import com.machine.service.scm.attribute.service.IScmAttributeGroupAttributeRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScmAttributeGroupAttributeRelationServiceImpl implements IScmAttributeGroupAttributeRelationService {

    @Autowired
    private IScmAttributeGroupAttributeRelationDao relationDao;

    @Autowired
    private IScmAttributeGroupDao attributeGroupDao;

    @Autowired
    private IScmAttributeDao attributeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmAttributeGroupAttributeRelationCreateInputDto inputDto) {
        validateGroupAndAttribute(inputDto.getGroupId(), inputDto.getAttributeId());
        if (relationDao.countByGroupAndAttribute(inputDto.getGroupId(), inputDto.getAttributeId()) > 0) {
            throw new ScmBusinessException("scm.attributeGroupAttributeRelation.service.create.alreadyExists", "组内已存在该属性");
        }
        ScmAttributeGroupAttributeRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeGroupAttributeRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByGroupId(String groupId) {
        return relationDao.deleteByGroupId(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByAttributeId(String attributeId) {
        return relationDao.deleteByAttributeId(attributeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmAttributeGroupAttributeRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    public ScmAttributeGroupAttributeRelationDetailOutputDto getById(IdRequest request) {
        ScmAttributeGroupAttributeRelationEntity entity = relationDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupAttributeRelationDetailOutputDto.class);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByGroupId(IdRequest request) {
        return relationDao.listByGroupId(request.getId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupAttributeRelationDetailOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationEntity> listByGroupIdSet(Set<String> groupIdSet) {
        return relationDao.listByGroupIdSet(groupIdSet);
    }

    @Override
    public List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByAttributeId(IdRequest request) {
        return relationDao.listByAttributeId(request.getId()).stream()
                .map(entity -> JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeGroupAttributeRelationDetailOutputDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listAttributeIdByGroupId(IdRequest request) {
        return relationDao.listAttributeIdByGroupId(request.getId());
    }

    private void validateGroupAndAttribute(String groupId, String attributeId) {
        ScmAttributeGroupEntity group = attributeGroupDao.getById(groupId);
        if (group == null) {
            throw new ScmBusinessException("scm.attributeGroupAttributeRelation.service.groupNotExists", "属性分组不存在");
        }
        ScmAttributeEntity attribute = attributeDao.getById(attributeId);
        if (attribute == null) {
            throw new ScmBusinessException("scm.attributeGroupAttributeRelation.service.attributeNotExists", "属性不存在");
        }
    }
}
