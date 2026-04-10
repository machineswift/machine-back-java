package com.machine.client.data.brand;

import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/brand",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataBrandClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataBrandCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataBrandUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated DataBrandUpdateStatusInputDto inputDto);

    @PostMapping("detail")
    DataBrandDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("page")
    PageResponse<DataBrandListOutputDto> page(@RequestBody @Validated DataBrandQueryPageInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, DataBrandDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

}



