package com.machine.service.data.file.material.service;

import com.machine.client.data.file.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IDataMaterialCategoryRelationService {

    List<DataMaterialCategoryRelationOutputDto> listByMaterialId(IdRequest request);

    List<DataMaterialCategoryRelationOutputDto> listByMaterialIdSet(IdSetRequest request);
}
