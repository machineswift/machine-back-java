package com.machine.client.data.file.material;

import com.machine.client.data.file.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file/material_category_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataMaterialCategoryRelationClient {

    @PostMapping("list_by_materialId")
    List<DataMaterialCategoryRelationOutputDto> listByMaterialId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_materialIdSet")
    List<DataMaterialCategoryRelationOutputDto> listByMaterialIdSet(@RequestBody @Validated IdSetRequest request);
}
