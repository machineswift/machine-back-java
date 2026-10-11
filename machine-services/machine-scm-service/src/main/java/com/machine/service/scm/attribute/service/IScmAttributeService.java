package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.input.ScmAttributeCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateAttributeGroupInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;

public interface IScmAttributeService {

    String create(ScmAttributeCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmAttributeUpdateInputDto inputDto);

    int updateCode(ScmAttributeUpdateCodeInputDto inputDto);

    int updateAttributeGroup(ScmAttributeUpdateAttributeGroupInputDto inputDto);

    int updateBackCategory(ScmAttributeUpdateBackCategoryInputDto inputDto);

    ScmAttributeDetailOutputDto getById(IdRequest request);

    List<ScmAttributeListOutputDto> listAll();

    List<ScmAttributeListOutputDto> listByIdSet(IdSetRequest request);

    PageResponse<ScmAttributeListOutputDto> selectPage(ScmAttributeQueryPageInputDto inputDto);
}