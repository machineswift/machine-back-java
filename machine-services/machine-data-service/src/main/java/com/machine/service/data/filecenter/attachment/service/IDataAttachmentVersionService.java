package com.machine.service.data.filecenter.attachment.service;

import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionDeleteInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionRollbackInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentVersionDetailOutputDto;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IDataAttachmentVersionService {

    String create(DataAttachmentVersionCreateInputDto inputDto);

    void delete(DataAttachmentVersionDeleteInputDto inputDto);

    void update(DataAttachmentVersionUpdateInputDto inputDto);

    void rollback(DataAttachmentVersionRollbackInputDto inputDto);

    DataAttachmentVersionDetailOutputDto getById(String id);

    List<DataAttachmentVersionDetailOutputDto> listByIdSet(IdSetRequest request);

}
