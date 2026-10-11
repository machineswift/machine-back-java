package com.machine.service.scm.attribute.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.dao.IScmAttributeValueRelationDao;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueRelationEntity;
import com.machine.service.scm.attribute.service.IScmAttributeValueRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScmAttributeValueRelationServiceImpl implements IScmAttributeValueRelationService {

    @Autowired
    private IScmAttributeValueRelationDao relationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmAttributeValueRelationCreateInputDto inputDto) {
        ScmAttributeValueRelationEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ScmAttributeValueRelationEntity.class);
        return relationDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        return relationDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByParentValueId(String parentValueId) {
        return relationDao.deleteByParentValueId(parentValueId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByChildAttributeId(String childAttributeId) {
        return relationDao.deleteByChildAttributeId(childAttributeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmAttributeValueRelationEntity entity) {
        return relationDao.update(entity);
    }

    @Override
    public int countByParentValueAndChildAttribute(String parentValueId, String childAttributeId) {
        return relationDao.countByParentValueAndChildAttribute(parentValueId, childAttributeId);
    }

    @Override
    public ScmAttributeValueRelationDetailOutputDto getById(IdRequest request) {
        ScmAttributeValueRelationEntity entity = relationDao.getById(request.getId());
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmAttributeValueRelationDetailOutputDto.class);
    }

    @Override
    public List<String> listChildAttributeIdByParentValueId(IdRequest request) {
        return relationDao.listChildAttributeIdByParentValueId(request.getId());
    }

    @Override
    public List<ScmAttributeValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet) {
        return relationDao.listByParentValueIdSet(parentValueIdSet);
    }
}