package com.machine.service.scm.category.service;

import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeGroupInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;

import java.util.List;

public interface IScmBackCategoryService {

    String create(ScmBackCategoryCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmBackCategoryUpdateInputDto inputDto);

    int updateParent(ScmBackCategoryUpdateParentInputDto inputDto);

    int updateAttributeGroup(ScmBackCategoryUpdateAttributeGroupInputDto inputDto);

    int updateAttribute(ScmBackCategoryUpdateAttributeInputDto inputDto);

    ScmBackCategoryDetailOutputDto getById(IdRequest request);

    List<ScmBackCategoryListOutputDto> listAll();

    Tuples.Tuple2<String, ScmBackCategoryTreeSimpleOutputDto> treeAllSimple();
}