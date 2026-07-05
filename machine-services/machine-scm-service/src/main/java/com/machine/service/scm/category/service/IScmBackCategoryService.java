package com.machine.service.scm.category.service;

import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmBackCategoryService {

    String create(ScmBackCategoryCreateInputDto inputDto);

    int update(ScmBackCategoryUpdateInputDto inputDto);

    int updateParent(ScmBackCategoryUpdateParentInputDto inputDto);

    int deleteById(IdRequest request);

    ScmBackCategoryDetailOutputDto getById(IdRequest request);

    ScmBackCategoryTreeSimpleOutputDto treeAllSimple();

    List<ScmBackCategoryListOutputDto> listAll();
}