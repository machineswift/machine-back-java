package com.machine.service.data.filecenter.attachment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;

public interface IDataAttachmentOperationLogService {

    void create(DataAttachmentOperationLogCreateInputDto inputDto);

    Page<DataAttachmentOperationLogListOutputDto> selectPage(DataAttachmentOperationLogPageInputDto inputDto);
}
