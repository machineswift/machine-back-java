package com.machine.service.data.supplier.service;

import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;
import java.util.Set;

public interface IDataSupplierUserService {

    String create(DataSupplierCreateInputDto inputDto);

    int update(DataSupplierUpdateInputDto inputDto);

    DataSupplierDetailOutputDto detail(IdRequest request);

    DataSupplierDetailOutputDto getByUserId(IdRequest request);

    Set<String> listUserIdByCondition(DataSupplierListUserIdInputDto inputDto);

    List<DataSupplierListOutputDto> listByOffset(DataSupplierQueryListOffsetInputDto inputDto);

}
