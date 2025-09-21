package com.machine.service.data.material.service;

import com.machine.client.data.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryListOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDataMaterialCategoryService {

    String create(DataMaterialCategoryCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataMaterialCategoryUpdateInputDto inputDto);

    int updateParent(DataMaterialCategoryUpdateParentInputDto inputDto);

    DataMaterialCategoryDetailOutputDto detail(IdRequest request);

    List<DataMaterialCategoryListOutputDto> listAll();

    DataMaterialCategoryTreeSimpleOutputDto treeAllSimple();
}
