package com.machine.service.data.filecenter.attachment.service;

import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;

public interface IDataFileTempService {

    String create(DataFileTempCreateInputDto inputDto);

    DataFileTempDetailOutputDto getById(String id);
}
