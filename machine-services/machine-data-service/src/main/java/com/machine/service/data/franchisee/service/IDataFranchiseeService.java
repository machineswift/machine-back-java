package com.machine.service.data.franchisee.service;

import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;
import java.util.Set;

public interface IDataFranchiseeService {

    String create(DataFranchiseeCreateInputDto inputDto);

    boolean bindShop(DataFranchiseeBindShopInputDto inputDto);

    boolean unbindShop(DataFranchiseeUnbindShopInputDto inputDto);

    int updatePhone(OpenApiFranchiseeUpdatePhoneInputDto inputDto);

    int update(DataFranchiseeUpdateInputDto inputDto);

    DataFranchiseeDetailOutputDto detail(IdRequest request);

    DataFranchiseeDetailOutputDto getByUserId(IdRequest request);

    DataFranchiseeDetailOutputDto detailByCode(IdRequest request);

    OpenapiFranchiseeIdentityCardOutputDto identityCard(IdRequest request);

    OpenapiFranchiseeHealthCertificateOutputDto healthCertificate(IdRequest request);

    Set<String> listUserIdByCondition(DataFranchiseeListUserIdInputDto inputDto);

    List<DataFranchiseeListOutputDto> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto);

}
