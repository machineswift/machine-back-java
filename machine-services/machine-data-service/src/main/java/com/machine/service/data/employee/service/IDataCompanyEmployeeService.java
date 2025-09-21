package com.machine.service.data.employee.service;

import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataCompanyEmployeeService {
    String create(DataCompanyEmployeeCreateInputDto inputDto);

    int update(DataCompanyEmployeeUpdateInputDto inputDto);

    DataCompanyEmployeeDetailOutputDto detail(IdRequest request);

    DataCompanyEmployeeDetailOutputDto getByUserId(IdRequest request);

    Set<String> listUserIdByCondition(DataCompanyEmployeeListUserIdInputDto inputDto);

    List<DataCompanyEmployeeListOutputDto> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto);

    Map<String, DataCompanyEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request);

}
