package com.machine.service.data.filecenter.attachment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogQueryPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IDataAttachmentOperationLogService {

    void create(DataAttachmentOperationLogCreateInputDto inputDto);

    /**
     * 附件操作日志详情
     */
    DataAttachmentOperationLogDetailOutputDto detail(IdRequest request);

    Page<DataAttachmentOperationLogListOutputDto> selectPage(DataAttachmentOperationLogQueryPageInputDto inputDto);
}
