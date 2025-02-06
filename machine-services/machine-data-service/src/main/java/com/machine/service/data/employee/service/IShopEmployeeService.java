package com.machine.service.data.employee.service;

import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IShopEmployeeService {

    String create(DataShopEmployeeCreateInputDto inputDto);

    int updateStatus(DataShopEmployeeUpdateStatusInputDto inputDto);

    int updatePhone(OpenapiShopEmployeeUpdatePhoneInputDto inputDto);

    int updateIdentityCard(OpenApiShopEmployeeUpdateIdentityCardInputDto inputDto);

    int updateHealthCertificate(OpenApiShopEmployeeUpdateHealthCertificateInputDto inputDto);

    int updateByUserId(DataShopEmployeeUpdateInputDto inputDto);

    ShopEmployeeDetailOutputDto detail(IdRequest request);

    ShopEmployeeDetailOutputDto getByUserId(IdRequest request);

    OpenapiShopEmployeeIdentityCardOutputDto identityCard(IdRequest request);

    OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(IdRequest request);

    List<ShopEmployeeListOutputDto> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto);

}
