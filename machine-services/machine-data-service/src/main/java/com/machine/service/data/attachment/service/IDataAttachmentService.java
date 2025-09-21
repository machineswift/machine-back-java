package com.machine.service.data.attachment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.dto.input.DataAttachmentBindTableNameInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentUpdateInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IDataAttachmentService {
    String createTemporary(DataAttachmentCreateTemporaryInputDto inputDto);

    void update(DataAttachmentUpdateInputDto inputDto);

    void bindTableName(DataAttachmentBindTableNameInputDto inputDto);

    DataAttachmentDetailOutputDto getById(IdRequest request);

    List<DataAttachmentDetailOutputDto> listByIdSet(IdSetRequest request);

    Page<DataAttachmentListOutputDto> selectPage(DataAttachmentQueryPageInputDto inputDto);
}
