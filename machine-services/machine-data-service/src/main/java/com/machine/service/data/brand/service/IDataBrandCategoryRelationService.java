package com.machine.service.data.brand.service;

import com.machine.client.data.brand.dto.output.DataBrandCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IDataBrandCategoryRelationService {

    List<DataBrandCategoryRelationOutputDto> listByBrandId(IdRequest request);

    List<DataBrandCategoryRelationOutputDto> listByBrandIdSet(IdSetRequest request);
}
