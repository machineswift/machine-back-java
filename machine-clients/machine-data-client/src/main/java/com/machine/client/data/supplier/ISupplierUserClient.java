package com.machine.client.data.supplier;

import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/supplier_user",
        configuration = OpenFeignDefaultConfig.class)
public interface ISupplierUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataSupplierCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataSupplierUpdateInputDto inputDto);

    @PostMapping("detail")
    DataSupplierDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("get_by_userId")
    DataSupplierDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_offset")
    List<DataSupplierListOutputDto> listByOffset(@RequestBody @Validated DataSupplierQueryListOffsetInputDto inputDto);

}
