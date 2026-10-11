package com.machine.client.data.brand;

import com.machine.client.data.brand.dto.input.DataBrandCategoryCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateParentInputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryListOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/brand/category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataBrandCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataBrandCategoryCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataBrandCategoryUpdateInputDto inputDto);

    @PostMapping("updateParent")
    int updateParent(@RequestBody @Validated DataBrandCategoryUpdateParentInputDto inputDto);

    @PostMapping("detail")
    DataBrandCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("list_all")
    List<DataBrandCategoryListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    Tuples.Tuple2<String, DataBrandCategoryTreeSimpleOutputDto> treeAllSimple();
}
