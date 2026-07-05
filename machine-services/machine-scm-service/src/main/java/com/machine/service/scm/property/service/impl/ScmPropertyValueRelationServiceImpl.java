package com.machine.service.scm.property.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.dto.input.ScmPropertyValueRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.IScmPropertyValueRelationDao;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueRelationEntity;
import com.machine.service.scm.property.service.IScmPropertyValueRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScmPropertyValueRelationServiceImpl implements IScmPropertyValueRelationService {

    @Autowired
    private IScmPropertyValueRelationDao relationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmPropertyValueRelationCreateInputDto inputDto) {
        ScmPropertyValueRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmPropertyValueRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmPropertyValueRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    public ScmPropertyValueRelationDetailOutputDto getById(IdRequest request) {
        ScmPropertyValueRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmPropertyValueRelationDetailOutputDto.class);
    }

    @Override
    public List<String> listChildPropertyIdByParentValueId(IdRequest request) {
        return relationDao.listChildPropertyIdByParentValueId(request.getId());
    }

    @Override
    public List<ScmPropertyValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet) {
        return relationDao.listByParentValueIdSet(parentValueIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByParentValueId(String parentValueId) {
        return relationDao.deleteByParentValueId(parentValueId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByChildPropertyId(String childPropertyId) {
        return relationDao.deleteByChildPropertyId(childPropertyId);
    }

    @Override
    public int countByParentValueAndChildProperty(String parentValueId, String childPropertyId) {
        return relationDao.countByParentValueAndChildProperty(parentValueId, childPropertyId);
    }
}