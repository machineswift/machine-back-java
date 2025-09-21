package com.machine.service.data.supplier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.*;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataSupplierCompanyService {

    String create(DataSupplierCompanyCreateInputDto inputDto);

    int updateStatus(DataSupplierCompanyUpdateStatusInputDto inputDto);

    int update(DataSupplierCompanyUpdateInputDto inputDto);

    DataSupplierCompanyDetailOutputDto detail(IdRequest request);

    List<DataSupplierCompanySimpleListOutputDto> listByIdSet(IdSetRequest request);

    List<DataSupplierCompanySimpleListOutputDto> listBySupplierId(IdRequest request);

    List<DataSupplierCompanySimpleListOutputDto> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto);

    Map<String, DataSupplierCompanySimpleListOutputDto> mapByIdSet(IdSetRequest request);

    Page<DataSupplierCompanySimpleListOutputDto> pageSample(DataSupplierCompanySimplePageInputDto inputDto);

    Page<DataSupplierCompanyListOutputDto> pageExpand(DataSupplierCompanyPageInputDto inputDto);
}
