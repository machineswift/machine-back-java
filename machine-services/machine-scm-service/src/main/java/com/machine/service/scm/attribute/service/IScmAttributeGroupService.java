package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateAttributeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;
import java.util.Map;

public interface IScmAttributeGroupService {

    String create(ScmAttributeGroupCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmAttributeGroupUpdateInputDto inputDto);

    int updateStatus(ScmAttributeGroupUpdateStatusInputDto inputDto);

    int updateBackCategory(ScmAttributeGroupUpdateBackCategoryInputDto inputDto);

    int updateAttribute(ScmAttributeGroupUpdateAttributeInputDto inputDto);

    int updateCode(ScmAttributeGroupUpdateCodeInputDto inputDto);

    Map<String, Integer> mapBackCategoryCountByGroupIdSet(IdSetRequest request);

    Map<String, Integer> mapAttributeCountByGroupIdSet(IdSetRequest request);

    Map<String, Integer> mapAttributeGroupCountByBackCategoryIdSet(IdSetRequest request);

    Map<String, Integer> mapAttributeGroupCountByAttributeIdSet(IdSetRequest request);

    Map<String, List<ScmAttributeGroupItemOutputDto>> mapAttributeGroupByBackCategoryIdSet(IdSetRequest request);

    ScmAttributeGroupDetailOutputDto getById(IdRequest request);

    List<ScmAttributeGroupBackCategoryItemDto> listBackCategoryByGroupId(IdRequest request);

    List<ScmAttributeGroupSimpleListOutputDto> listByIdSet(IdSetRequest request);

    List<ScmAttributeGroupSimpleListOutputDto> listSimple(ScmAttributeGroupQueryListInputDto inputDto);

    PageResponse<ScmAttributeGroupListOutputDto> pageExpand(ScmAttributeGroupQueryPageInputDto inputDto);

}