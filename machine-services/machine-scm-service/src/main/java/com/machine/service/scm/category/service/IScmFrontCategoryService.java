package com.machine.service.scm.category.service;

import com.machine.client.scm.category.dto.input.ScmFrontCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmFrontCategoryService {

    String create(ScmFrontCategoryCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmFrontCategoryUpdateInputDto inputDto);

    int updateParent(ScmFrontCategoryUpdateParentInputDto inputDto);

    ScmFrontCategoryDetailOutputDto getById(IdRequest request);

    ScmFrontCategoryTreeOutputDto treeAllSimple();

    List<ScmFrontCategoryListOutputDto> listAll();
}