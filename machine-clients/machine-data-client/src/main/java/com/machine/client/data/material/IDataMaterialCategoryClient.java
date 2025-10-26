package com.machine.client.data.material;

import com.machine.client.data.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryListOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/material_category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataMaterialCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataMaterialCategoryCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataMaterialCategoryUpdateInputDto inputDto);

    @PostMapping("updateParent")
    int updateParent(@RequestBody @Validated DataMaterialCategoryUpdateParentInputDto inputDto);

    @PostMapping("detail")
    DataMaterialCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("list_all")
    List<DataMaterialCategoryListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    DataMaterialCategoryTreeSimpleOutputDto treeAllSimple();

}



