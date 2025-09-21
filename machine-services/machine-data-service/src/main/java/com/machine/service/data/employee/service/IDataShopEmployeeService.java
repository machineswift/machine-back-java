package com.machine.service.data.employee.service;

import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;
import java.util.Set;

public interface IDataShopEmployeeService {

    String create(DataShopEmployeeCreateInputDto inputDto);

    int updateByUserId(DataShopEmployeeUpdateInputDto inputDto);

    DataShopEmployeeDetailOutputDto detail(IdRequest request);

    DataShopEmployeeDetailOutputDto getByUserId(IdRequest request);

    OpenapiShopEmployeeIdentityCardOutputDto identityCard(IdRequest request);

    OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(IdRequest request);

    Set<String> listUserIdByCondition(DataShopEmployeeListUserIdInputDto inputDto);

    List<DataShopEmployeeListOutputDto> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto);

}
