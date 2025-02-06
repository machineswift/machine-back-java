package com.machine.service.data.employee.service;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface ICompanyEmployeeService {
    String create(DataCompanyEmployeeCreateInputDto inputDto);

    int update(DataCompanyEmployeeUpdateInputDto inputDto);

    CompanyEmployeeDetailOutputDto detail(IdRequest request);

    CompanyEmployeeDetailOutputDto getByUserId(IdRequest request);
    
    List<CompanyEmployeeListOutputDto> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto);

    Map<String, CompanyEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request);
}
