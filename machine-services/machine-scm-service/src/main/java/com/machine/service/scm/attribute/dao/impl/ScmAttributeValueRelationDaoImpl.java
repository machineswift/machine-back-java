package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.machine.service.scm.attribute.dao.IScmAttributeValueRelationDao;
import com.machine.service.scm.attribute.dao.mapper.ScmAttributeValueRelationMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmAttributeValueRelationDaoImpl implements IScmAttributeValueRelationDao {

    @Autowired
    private ScmAttributeValueRelationMapper relationMapper;

    @Override
    public String insert(ScmAttributeValueRelationEntity entity) {
        relationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return relationMapper.deleteById(id);
    }

    @Override
    public int deleteByParentValueId(String parentValueId) {
        return relationMapper.deleteByParentValueId(parentValueId);
    }

    @Override
    public int deleteByParentValueIdSet(Set<String> parentValueIdSet) {
        if (CollectionUtil.isEmpty(parentValueIdSet)) {
            return 0;
        }
        return relationMapper.deleteByParentValueIdSet(parentValueIdSet);
    }

    @Override
    public int deleteByChildAttributeId(String childAttributeId) {
        return relationMapper.deleteByChildAttributeId(childAttributeId);
    }

    @Override
    public int update(ScmAttributeValueRelationEntity entity) {
        return relationMapper.updateById(entity);
    }

    @Override
    public int countByParentValueId(String parentValueId) {
        return relationMapper.countByParentValueId(parentValueId);
    }

    @Override
    public int countByParentValueAndChildAttribute(String parentValueId, String childAttributeId) {
        return relationMapper.countByParentValueAndChildAttribute(parentValueId, childAttributeId);
    }

    @Override
    public ScmAttributeValueRelationEntity getById(String id) {
        return relationMapper.selectById(id);
    }

    @Override
    public List<String> listChildAttributeIdByParentValueId(String parentValueId) {
        return relationMapper.listChildAttributeIdByParentValueId(parentValueId);
    }

    @Override
    public List<ScmAttributeValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet) {
        return relationMapper.listByParentValueIdSet(parentValueIdSet);
    }
}