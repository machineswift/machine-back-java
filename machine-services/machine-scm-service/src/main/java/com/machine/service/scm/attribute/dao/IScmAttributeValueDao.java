package com.machine.service.scm.attribute.dao;

import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueEntity;

import java.util.List;

public interface IScmAttributeValueDao {

    String insert(ScmAttributeValueEntity entity);

    int deleteById(String id);

    int update(ScmAttributeValueEntity entity);

    int countByAttributeIdAndCode(String attributeId, String code);

    int countByAttributeIdAndName(String attributeId, String name);

    ScmAttributeValueEntity getByAttributeIdAndCode(String attributeId, String code);

    ScmAttributeValueEntity getByAttributeIdAndName(String attributeId, String name);

    ScmAttributeValueEntity getById(String id);

    List<ScmAttributeValueEntity> listByAttributeId(String attributeId);

}