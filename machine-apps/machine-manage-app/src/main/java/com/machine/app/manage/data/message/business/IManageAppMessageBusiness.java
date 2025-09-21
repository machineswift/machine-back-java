package com.machine.app.manage.data.message.business;

import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessagePageReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageReadMessageRequestVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageListResVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IManageAppMessageBusiness {

    PageResponse<ManageAppMessageListResVo> getMessagePage(ManageAppMessagePageReqVo request);

    Boolean read(ManageReadMessageRequestVo request);
}
