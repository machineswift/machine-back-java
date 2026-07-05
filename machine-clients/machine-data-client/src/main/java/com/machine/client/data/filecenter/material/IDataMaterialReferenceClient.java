package com.machine.client.data.filecenter.material;

import com.machine.client.data.filecenter.material.dto.input.DataMaterialReferenceQueryInputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialReferenceDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/file/material/reference",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataMaterialReferenceClient {

    @PostMapping("list_by_materialId")
    List<DataMaterialReferenceDetailOutputDto> listByMaterialId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_materialIdSet")
    Map<String, List<DataMaterialReferenceDetailOutputDto>> mapByMaterialIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_entity")
    List<DataMaterialReferenceDetailOutputDto> listByEntity(@RequestBody @Validated DataMaterialReferenceQueryInputDto inputDto);
}
