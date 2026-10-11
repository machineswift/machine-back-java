package com.machine.client.data.brand;

import com.machine.client.data.brand.dto.output.DataBrandCategoryRelationOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/brand/category_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataBrandCategoryRelationClient {

    @PostMapping("list_by_brandId")
    List<DataBrandCategoryRelationOutputDto> listByBrandId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_brandIdSet")
    List<DataBrandCategoryRelationOutputDto> listByBrandIdSet(@RequestBody @Validated IdSetRequest request);
}
