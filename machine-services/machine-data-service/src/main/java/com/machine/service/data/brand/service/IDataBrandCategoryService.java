package com.machine.service.data.brand.service;

import com.machine.client.data.brand.dto.input.DataBrandCategoryCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateParentInputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryListOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;

import java.util.List;

public interface IDataBrandCategoryService {

    String create(DataBrandCategoryCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataBrandCategoryUpdateInputDto inputDto);

    int updateParent(DataBrandCategoryUpdateParentInputDto inputDto);

    DataBrandCategoryDetailOutputDto detail(IdRequest request);

    List<DataBrandCategoryListOutputDto> listAll();

    Tuples.Tuple2<String, DataBrandCategoryTreeSimpleOutputDto> treeAllSimple();
}
