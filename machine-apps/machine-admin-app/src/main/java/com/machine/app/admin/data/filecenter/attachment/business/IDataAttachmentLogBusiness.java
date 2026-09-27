package com.machine.app.admin.data.filecenter.attachment.business;

import com.machine.app.admin.data.filecenter.attachment.controller.vo.request.DataAttachmentLogQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogDetailResponseVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

/**
 * 附件操作日志业务接口。
 */
public interface IDataAttachmentLogBusiness {

    /**
     * 附件操作日志详情
     */
    DataAttachmentLogDetailResponseVo detail(IdRequest request);

    /**
     * 附件操作日志分页查询
     */
    PageResponse<DataAttachmentLogExpandListResponseVo> pageExpand(DataAttachmentLogQueryPageRequestVo request);
}
