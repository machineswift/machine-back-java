package com.machine.service.data.filecenter.attachment.service;

import com.machine.client.data.filecenter.attachment.dto.output.DataFileDetailOutputDto;

import java.util.List;
import java.util.Set;

public interface IDataFileService {

    DataFileDetailOutputDto getById(String id);

    List<DataFileDetailOutputDto> listByIdSet(Set<String> idSet);
}
