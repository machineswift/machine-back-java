package com.machine.service.data.filecenter.attachment.service;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataAttachmentService {

    String create(DataAttachmentCreateInputDto inputDto);

    DataAttachmentDetailOutputDto getById(IdRequest request);

    List<DataAttachmentDetailOutputDto> listByIdSet(IdSetRequest request);

    DataAttachmentWithCurrentFileInfoOutputDto getCurrentByAttachmentId(IdRequest request);

    Map<String, DataAttachmentWithCurrentFileInfoOutputDto> mapCurrentByAttachmentIdSet(IdSetRequest request);

}
