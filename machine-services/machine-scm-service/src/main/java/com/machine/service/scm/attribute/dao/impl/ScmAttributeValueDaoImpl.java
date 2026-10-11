package com.machine.service.scm.attribute.dao.impl;

import com.machine.service.scm.attribute.dao.IScmAttributeValueDao;
import com.machine.service.scm.attribute.dao.mapper.ScmAttributeValueMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScmAttributeValueDaoImpl implements IScmAttributeValueDao {

    @Autowired
    private ScmAttributeValueMapper attributeValueMapper;

    @Override
    public String insert(ScmAttributeValueEntity entity) {
        attributeValueMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return attributeValueMapper.deleteById(id);
    }

    @Override
    public int update(ScmAttributeValueEntity entity) {
        return attributeValueMapper.updateById(entity);
    }

    @Override
    public int countByAttributeIdAndCode(String attributeId, String code) {
        return attributeValueMapper.countByAttributeIdAndCode(attributeId, code);
    }

    @Override
    public int countByAttributeIdAndName(String attributeId, String name) {
        return attributeValueMapper.countByAttributeIdAndName(attributeId, name);
    }

    @Override
    public ScmAttributeValueEntity getByAttributeIdAndCode(String attributeId, String code) {
        return attributeValueMapper.getByAttributeIdAndCode(attributeId, code);
    }

    @Override
    public ScmAttributeValueEntity getByAttributeIdAndName(String attributeId, String name) {
        return attributeValueMapper.getByAttributeIdAndName(attributeId, name);
    }

    @Override
    public ScmAttributeValueEntity getById(String id) {
        return attributeValueMapper.selectById(id);
    }

    @Override
    public List<ScmAttributeValueEntity> listByAttributeId(String attributeId) {
        return attributeValueMapper.listByAttributeId(attributeId);
    }

}