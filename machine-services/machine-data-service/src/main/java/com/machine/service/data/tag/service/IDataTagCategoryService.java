package com.machine.service.data.tag.service;

import com.machine.client.data.tag.dto.input.DataTagCategoryCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateParentInputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryListOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDataTagCategoryService {

    String create(DataTagCategoryCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataTagCategoryUpdateInputDto inputDto);

    int updateParent(DataTagCategoryUpdateParentInputDto inputDto);

    DataTagCategoryDetailOutputDto detail(IdRequest request);

    List<DataTagCategoryListOutputDto> listAllByType(ProfileSubjectTypeEnum type);

    DataTagCategoryTreeSimpleOutputDto treeAllSimple(ProfileSubjectTypeEnum type);
}
