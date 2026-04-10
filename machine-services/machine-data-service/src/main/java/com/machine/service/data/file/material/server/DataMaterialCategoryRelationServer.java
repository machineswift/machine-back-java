package com.machine.service.data.file.material.server;

import com.machine.client.data.file.material.IDataMaterialCategoryRelationClient;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.file.material.service.IDataMaterialCategoryRelationService;
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
@RequestMapping("server/data/file/material_category_relation")
public class DataMaterialCategoryRelationServer implements IDataMaterialCategoryRelationClient {

    @Autowired
    private IDataMaterialCategoryRelationService materialCategoryRelationService;

    @Override
    @PostMapping("list_by_materialId")
    public List<DataMaterialCategoryRelationOutputDto> listByMaterialId(@RequestBody @Validated IdRequest request) {
        return materialCategoryRelationService.listByMaterialId(request);
    }

    @Override
    @PostMapping("list_by_materialIdSet")
    public List<DataMaterialCategoryRelationOutputDto> listByMaterialIdSet(@RequestBody @Validated IdSetRequest request) {
        return materialCategoryRelationService.listByMaterialIdSet(request);
    }
}
