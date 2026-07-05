package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmPropertyCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;

public interface IScmPropertyService {

    String create(ScmPropertyCreateInputDto inputDto);

    int update(ScmPropertyUpdateInputDto inputDto);

    int deleteById(IdRequest request);

    ScmPropertyDetailOutputDto getById(IdRequest request);

    List<ScmPropertyListOutputDto> listAll();

    PageResponse<ScmPropertyListOutputDto> selectPage(ScmPropertyQueryPageInputDto inputDto);
}