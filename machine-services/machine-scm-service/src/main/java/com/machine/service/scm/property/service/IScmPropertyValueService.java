package com.machine.service.scm.property.service;

import com.machine.client.scm.property.dto.input.ScmPropertyValueCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyValueEntity;

import java.util.List;
import java.util.Set;

public interface IScmPropertyValueService {

    String create(ScmPropertyValueCreateInputDto inputDto);

    int update(ScmPropertyValueUpdateInputDto inputDto);

    int deleteById(IdRequest request);

    ScmPropertyValueDetailOutputDto getById(IdRequest request);

    List<ScmPropertyValueListOutputDto> listByPropertyId(ScmPropertyValueQueryInputDto inputDto);

    List<ScmPropertyValueEntity> listByPropertyIdSet(Set<String> propertyIdSet);

    int deleteByPropertyId(String propertyId);

    int countByPropertyIdAndValue(String propertyId, String value);
}