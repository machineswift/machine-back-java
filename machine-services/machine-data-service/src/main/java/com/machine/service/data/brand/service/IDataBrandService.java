package com.machine.service.data.brand.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateLogoAttachmentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateParentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDataBrandService  {

    String create(DataBrandCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataBrandUpdateInputDto inputDto);

    int updateStatus(DataBrandUpdateStatusInputDto inputDto);

    int updateLogoAttachmentId(DataBrandUpdateLogoAttachmentIdInputDto inputDto);

    int updateParent(DataBrandUpdateParentIdInputDto inputDto);

    boolean exists(IdRequest request);

    DataBrandDetailOutputDto detail(IdRequest request);

    Set<String> listHasChildrenIdSet(IdSetRequest request);

    List<DataBrandDetailOutputDto> listAncestorById(IdRequest request);

    Map<String, DataBrandDetailOutputDto> mapByIdSet(IdSetRequest request);

    Page<DataBrandListOutputDto> page(DataBrandQueryPageInputDto inputDto);

    Page<DataBrandListOutputDto> childrenPage(DataBrandQueryPageInputDto inputDto);

    Page<DataBrandListOutputDto> simplePage(DataBrandQuerySimplePageInputDto inputDto);

}
