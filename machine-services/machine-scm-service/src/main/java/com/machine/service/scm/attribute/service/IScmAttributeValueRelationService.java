package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.input.ScmAttributeValueRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeValueRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeValueRelationService {

    String create(ScmAttributeValueRelationCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int deleteByParentValueId(String parentValueId);

    int deleteByChildAttributeId(String childAttributeId);

    int update(ScmAttributeValueRelationEntity entity);

    int countByParentValueAndChildAttribute(String parentValueId, String childAttributeId);

    ScmAttributeValueRelationDetailOutputDto getById(IdRequest request);

    List<String> listChildAttributeIdByParentValueId(IdRequest request);

    List<ScmAttributeValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet);
}