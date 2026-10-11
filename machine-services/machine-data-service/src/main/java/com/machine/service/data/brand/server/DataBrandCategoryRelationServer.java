package com.machine.service.data.brand.server;

import com.machine.client.data.brand.IDataBrandCategoryRelationClient;
import com.machine.client.data.brand.dto.output.DataBrandCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.brand.service.IDataBrandCategoryRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/brand/category_relation")
public class DataBrandCategoryRelationServer implements IDataBrandCategoryRelationClient {

    @Autowired
    private IDataBrandCategoryRelationService brandCategoryRelationService;

    @Override
    @PostMapping("list_by_brandId")
    public List<DataBrandCategoryRelationOutputDto> listByBrandId(@RequestBody @Validated IdRequest request) {
        return brandCategoryRelationService.listByBrandId(request);
    }

    @Override
    @PostMapping("list_by_brandIdSet")
    public List<DataBrandCategoryRelationOutputDto> listByBrandIdSet(@RequestBody @Validated IdSetRequest request) {
        return brandCategoryRelationService.listByBrandIdSet(request);
    }
}
