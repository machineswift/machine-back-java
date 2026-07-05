package com.machine.service.data.filecenter.material.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.material.dto.input.*;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IDataMaterialService {

    String create(DataMaterialCreateInputDto inputDto);

    void update(DataMaterialUpdateInputDto inputDto);

    void updateCategory(DataMaterialUpdateCategoryInputDto inputDto);

    void updateAttachmentId(DataMaterialUpdateAttachmentIdInputDto inputDto);

    DataMaterialDetailOutputDto getById(IdRequest request);

    List<DataMaterialDetailOutputDto> listByIdSet(IdSetRequest request);

    Page<DataMaterialListOutputDto> selectPage(DataMaterialQueryPageInputDto inputDto);

}
