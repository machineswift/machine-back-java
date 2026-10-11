package com.machine.app.partner.data.message.business;

import com.machine.app.partner.data.message.controller.vo.request.SuperAppMessageGroupCountRequestVo;
import com.machine.app.partner.data.message.controller.vo.request.SuperMessagePageRequestVo;
import com.machine.app.partner.data.message.controller.vo.request.SuperMessageUnreadCountRequestVo;
import com.machine.app.partner.data.message.controller.vo.request.SuperReadMessageRequestVo;
import com.machine.app.partner.data.message.controller.vo.response.SuperAppMessageGroupCountResponseVo;
import com.machine.app.partner.data.message.controller.vo.response.SuperAppMessageListResponseVo;
import com.machine.sdk.base.envm.BaseEnum;
import com.machine.sdk.base.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;

public interface ISuperMessageBusiness {
    Boolean read(SuperReadMessageRequestVo request);

    Integer getUnreadCount(SuperMessageUnreadCountRequestVo request);

    List<SuperAppMessageGroupCountResponseVo> groupCount(SuperAppMessageGroupCountRequestVo request);

    PageResponse<SuperAppMessageListResponseVo> page(SuperMessagePageRequestVo request);

    PageResponse<SuperAppMessageListResponseVo> customCategoryPage(DataMessageChannelEnum channelEnum, BaseEnum baseEnum, Long current, Long size);

}
