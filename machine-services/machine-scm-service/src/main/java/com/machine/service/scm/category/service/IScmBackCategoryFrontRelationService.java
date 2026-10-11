package com.machine.service.scm.category.service;

import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmBackCategoryFrontRelationService {

    ScmBackCategoryFrontRelationDetailOutputDto getById(IdRequest request);

    List<ScmBackCategoryFrontRelationListOutputDto> listAll();

    List<ScmBackCategoryFrontRelationListOutputDto> listByFrontCategoryId(IdRequest request);

    List<ScmBackCategoryFrontRelationListOutputDto> listByBackCategoryId(IdRequest request);

}