package com.machine.service.data.file.attachment.service;

import com.machine.client.data.file.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.file.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IDataAttachmentService {

    String create(DataAttachmentCreateInputDto inputDto);

    DataAttachmentDetailOutputDto getById(IdRequest request);

    List<DataAttachmentDetailOutputDto> listByIdSet(IdSetRequest request);

}
