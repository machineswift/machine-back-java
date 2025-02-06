package com.machine.service.data.supplier.service;

import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface ISupplierUserService {

    String create(DataSupplierCreateInputDto inputDto);

    int update(DataSupplierUpdateInputDto inputDto);

    DataSupplierDetailOutputDto detail(IdRequest request);

    DataSupplierDetailOutputDto getByUserId(IdRequest request);

    List<DataSupplierListOutputDto> listByOffset(DataSupplierQueryListOffsetInputDto inputDto);

}
