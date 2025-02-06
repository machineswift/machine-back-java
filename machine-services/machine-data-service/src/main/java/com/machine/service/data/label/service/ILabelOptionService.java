package com.machine.service.data.label.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.dto.input.*;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface ILabelOptionService {

    String create(DataLabelOptionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataLabelOptionUpdateInputDto inputDto);

    int updateStatus(DataLabelOptionUpdateStatusInputDto inputDto);

    DataLabelOptionDetailOutputDto detail(IdRequest request);

    List<DataLabelOptionListOutputDto> listByIdSet(IdSetRequest request);

    List<DataLabelOptionListOutputDto> listByOffset(DataLabelOptionQueryListOffsetInputDto inputDto);

    Map<String, DataLabelOptionListOutputDto> mapByIdSet(IdSetRequest request);

    Page<DataLabelOptionListOutputDto> page(DataLabelOptionQueryPageInputDto inputDto);

}
