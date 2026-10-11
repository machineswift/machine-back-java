package com.machine.service.scm.attribute.service;

import com.machine.client.scm.attribute.dto.input.ScmAttributeValueCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueQueryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueSimpleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmAttributeValueService {

    String create(ScmAttributeValueCreateInputDto inputDto);

    int deleteById(IdRequest request);

    int update(ScmAttributeValueUpdateInputDto inputDto);

    int updateStatus(ScmAttributeValueUpdateStatusInputDto inputDto);

    int updateCode(ScmAttributeValueUpdateCodeInputDto inputDto);

    ScmAttributeValueDetailOutputDto getById(IdRequest request);

    List<ScmAttributeValueSimpleListOutputDto> listSimple(ScmAttributeValueQueryInputDto inputDto);

    List<ScmAttributeValueListOutputDto> listExpand(ScmAttributeValueQueryInputDto inputDto);

}