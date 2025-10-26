package com.machine.client.data.material;

import com.machine.client.data.material.dto.input.DataMaterialCreatePermanentInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCreateTemporaryInputDto;
import com.machine.client.data.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/material",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataMaterialClient {

    @PostMapping("create_temporary")
    String createTemporary(@RequestBody @Validated DataMaterialCreateTemporaryInputDto inputDto);

    @PostMapping("create_permanent")
    void createPermanent(@RequestBody @Validated DataMaterialCreatePermanentInputDto inputDto);

    @PostMapping("update_permanent")
    void updatePermanent(@RequestBody @Validated DataMaterialUpdateInputDto inputDto);

    @PostMapping("get_by_id")
    DataMaterialDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataMaterialDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("select_page")
    PageResponse<DataMaterialListOutputDto> selectPage(@RequestBody @Validated DataMaterialQueryPageInputDto inputDto);
}



