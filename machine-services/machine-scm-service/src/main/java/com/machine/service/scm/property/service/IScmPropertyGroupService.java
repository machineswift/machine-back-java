package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmPropertyGroupCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmPropertyGroupService {

    String create(ScmPropertyGroupCreateInputDto inputDto);

    int update(ScmPropertyGroupUpdateInputDto inputDto);

    int deleteById(IdRequest request);

    ScmPropertyGroupDetailOutputDto getById(IdRequest request);

    List<ScmPropertyGroupListOutputDto> listByBackCategoryId(ScmPropertyGroupQueryInputDto inputDto);

}