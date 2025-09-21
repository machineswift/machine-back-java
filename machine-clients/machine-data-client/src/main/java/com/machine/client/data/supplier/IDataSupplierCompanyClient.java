package com.machine.client.data.supplier;

import com.machine.client.data.supplier.dto.input.*;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/supplier_company",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataSupplierCompanyClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataSupplierCompanyCreateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated DataSupplierCompanyUpdateStatusInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataSupplierCompanyUpdateInputDto inputDto);

    @PostMapping("detail")
    DataSupplierCompanyDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_idSet")
    List<DataSupplierCompanySimpleListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_supplierId")
    List<DataSupplierCompanySimpleListOutputDto> listBySupplierId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_offset")
    List<DataSupplierCompanySimpleListOutputDto> listByOffset(@RequestBody @Validated DataSupplierCompanyQueryListOffsetInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, DataSupplierCompanySimpleListOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("page_sample")
    PageResponse<DataSupplierCompanySimpleListOutputDto> pageSample(@RequestBody @Validated DataSupplierCompanySimplePageInputDto inputDto);

    @PostMapping("page_expand")
    PageResponse<DataSupplierCompanyListOutputDto> pageExpand(@RequestBody @Validated DataSupplierCompanyPageInputDto inputDto);

}
