package com.machine.client.data.supplier;

import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/supplier_user",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataSupplierUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataSupplierCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataSupplierUpdateInputDto inputDto);

    @PostMapping("detail")
    DataSupplierDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    DataSupplierDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_userId_by_condition")
    Set<String> listUserIdByCondition(@RequestBody @Validated DataSupplierListUserIdInputDto inputDto);

    @PostMapping("list_by_offset")
    List<DataSupplierListOutputDto> listByOffset(@RequestBody @Validated DataSupplierQueryListOffsetInputDto inputDto);

}



