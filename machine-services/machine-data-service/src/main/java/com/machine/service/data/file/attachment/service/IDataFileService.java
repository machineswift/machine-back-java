package com.machine.service.data.file.attachment.service;

import com.machine.client.data.file.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IDataFileService {

    DataFileDetailOutputDto getById(IdRequest request);

    List<DataFileDetailOutputDto> listByIdSet(IdSetRequest request);

}
