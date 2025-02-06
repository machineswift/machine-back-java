package com.machine.app.manage.data.message.business;

import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateListReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateChannelReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateStatusReqVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageTemplateListResVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IManageAppMessageTemplateBusiness {

    PageResponse<ManageAppMessageTemplateListResVo> getMessageTemplatePage(ManageAppMessageTemplateListReqVo request);

    void updateMessageTemplate(ManageAppMessageTemplateUpdateReqVo request);

    void updateMessageTemplateStatus(ManageAppMessageTemplateUpdateStatusReqVo request);

    void updateMessageTemplateChannel(ManageAppMessageTemplateUpdateChannelReqVo request);
}
