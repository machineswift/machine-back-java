package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmPropertyValueRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyValueRelationService {

    String create(ScmPropertyValueRelationCreateInputDto inputDto);

    int update(ScmPropertyValueRelationEntity entity);

    int deleteById(IdRequest request);

    ScmPropertyValueRelationDetailOutputDto getById(IdRequest request);

    List<String> listChildPropertyIdByParentValueId(IdRequest request);

    List<ScmPropertyValueRelationEntity> listByParentValueIdSet(Set<String> parentValueIdSet);

    int deleteByParentValueId(String parentValueId);

    int deleteByChildPropertyId(String childPropertyId);

    int countByParentValueAndChildProperty(String parentValueId, String childPropertyId);
}