package com.machine.service.data.label.service;

import com.machine.client.data.label.dto.input.*;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataLabelOptionService {

    String create(DataLabelOptionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataLabelOptionUpdateInputDto inputDto);

    int updateStatus(DataLabelOptionUpdateStatusInputDto inputDto);

    DataLabelOptionDetailOutputDto detail(IdRequest request);

    List<DataLabelOptionListOutputDto> listByIdSet(IdSetRequest request);

    Map<String, DataLabelOptionListOutputDto> mapByIdSet(IdSetRequest request);

}
