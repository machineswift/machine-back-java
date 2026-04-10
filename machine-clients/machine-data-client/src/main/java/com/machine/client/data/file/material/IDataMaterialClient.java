package com.machine.client.data.file.material;

import com.machine.client.data.file.material.dto.input.DataMaterialCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateCategoryInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file/material",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataMaterialClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataMaterialCreateInputDto inputDto);

    @PostMapping("update")
    void update(@RequestBody @Validated DataMaterialUpdateInputDto inputDto);

    @PostMapping("update_category")
    void updateCategory(@RequestBody @Validated DataMaterialUpdateCategoryInputDto inputDto);

    @PostMapping("get_by_id")
    DataMaterialDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataMaterialDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("select_page")
    PageResponse<DataMaterialListOutputDto> selectPage(@RequestBody @Validated DataMaterialQueryPageInputDto inputDto);
}



