package com.machine.service.scm.category.service;

import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmFrontBackCategoryRelationService {

    ScmFrontBackCategoryRelationDetailOutputDto getById(IdRequest request);

    List<ScmFrontBackCategoryRelationListOutputDto> listAll();

    List<ScmFrontBackCategoryRelationListOutputDto> listByFrontCategoryId(IdRequest request);

}