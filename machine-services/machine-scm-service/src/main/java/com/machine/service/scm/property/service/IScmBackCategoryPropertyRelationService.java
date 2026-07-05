package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmBackCategoryPropertyRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmBackCategoryPropertyRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.mapper.entity.ScmBackCategoryPropertyRelationEntity;

import java.util.List;
import java.util.Set;

public interface IScmBackCategoryPropertyRelationService {

    String create(ScmBackCategoryPropertyRelationCreateInputDto inputDto);

    int update(ScmBackCategoryPropertyRelationEntity entity);

    int deleteById(IdRequest request);

    ScmBackCategoryPropertyRelationDetailOutputDto getById(IdRequest request);

    List<ScmBackCategoryPropertyRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

}