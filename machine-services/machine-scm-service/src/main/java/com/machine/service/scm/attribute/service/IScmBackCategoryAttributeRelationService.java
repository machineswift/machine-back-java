package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.input.ScmBackCategoryAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmBackCategoryAttributeRelationEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IScmBackCategoryAttributeRelationService {

    String create(ScmBackCategoryAttributeRelationCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmBackCategoryAttributeRelationEntity entity);

    ScmBackCategoryAttributeRelationDetailOutputDto getById(IdRequest request);

    List<ScmBackCategoryAttributeRelationEntity> listByBackCategoryIdSet(Set<String> backCategoryIdSet);

    List<ScmBackCategoryAttributeRelationDetailOutputDto> listByAttributeId(IdRequest request);

    List<ScmBackCategoryAttributeRelationDetailOutputDto> listByBackCategoryId(IdRequest request);

    Map<String, Integer> mapAttributeCountByBackCategoryIdSet(IdSetRequest request);

    Map<String, Integer> mapBackCategoryCountByAttributeIdSet(IdSetRequest request);

}