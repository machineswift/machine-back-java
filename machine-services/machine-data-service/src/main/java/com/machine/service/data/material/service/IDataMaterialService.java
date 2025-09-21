package com.machine.service.data.material.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.material.dto.input.DataMaterialCreatePermanentInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCreateTemporaryInputDto;
import com.machine.client.data.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IDataMaterialService {

    String createTemporary(DataMaterialCreateTemporaryInputDto inputDto);

    void createPermanent(DataMaterialCreatePermanentInputDto inputDto);

    void updatePermanent(DataMaterialUpdateInputDto inputDto);
    
    DataMaterialDetailOutputDto getById(IdRequest request);

    List<DataMaterialDetailOutputDto> listByIdSet(IdSetRequest request);

    Page<DataMaterialListOutputDto> selectPage(DataMaterialQueryPageInputDto inputDto);
}
