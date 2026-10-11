package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupAttributeRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmAttributeGroupAttributeRelationService {

    String create(ScmAttributeGroupAttributeRelationCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int deleteByGroupId(String groupId);

    int deleteByAttributeId(String attributeId);

    int update(ScmAttributeGroupAttributeRelationEntity entity);

    ScmAttributeGroupAttributeRelationDetailOutputDto getById(IdRequest request);

    List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByGroupId(IdRequest request);

    List<ScmAttributeGroupAttributeRelationEntity> listByGroupIdSet(Set<String> groupIdSet);

    List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByAttributeId(IdRequest request);

    List<String> listAttributeIdByGroupId(IdRequest request);

}