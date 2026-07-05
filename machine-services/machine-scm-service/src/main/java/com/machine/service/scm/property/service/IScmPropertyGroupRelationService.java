package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmPropertyGroupRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyGroupRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyGroupRelationService {

    String create(ScmPropertyGroupRelationCreateInputDto inputDto);

    int update(ScmPropertyGroupRelationEntity entity);

    int deleteById(IdRequest request);

    ScmPropertyGroupRelationDetailOutputDto getById(IdRequest request);

    List<String> listPropertyIdByGroupId(IdRequest request);

    List<ScmPropertyGroupRelationEntity> listByGroupIdSet(Set<String> groupIdSet);

    int deleteByGroupId(String groupId);

    int deleteByPropertyId(String propertyId);

}