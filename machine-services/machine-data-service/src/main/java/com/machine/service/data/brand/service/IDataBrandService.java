package com.machine.service.data.brand.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.Map;

public interface IDataBrandService  {

    String create(DataBrandCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataBrandUpdateInputDto inputDto);

    int updateStatus(DataBrandUpdateStatusInputDto inputDto);

    DataBrandDetailOutputDto detail(IdRequest request);

    Page<DataBrandListOutputDto> page(DataBrandQueryPageInputDto inputDto);

    Map<String, DataBrandDetailOutputDto> mapByIdSet(IdSetRequest request);
}
