package com.machine.app.suprr.data.message.business;

import com.machine.app.suprr.data.message.controller.vo.request.SuperAppMessageGroupCountRequestVo;
import com.machine.app.suprr.data.message.controller.vo.request.SuperMessagePageRequestVo;
import com.machine.app.suprr.data.message.controller.vo.request.SuperMessageUnreadCountRequestVo;
import com.machine.app.suprr.data.message.controller.vo.request.SuperReadMessageRequestVo;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageGroupCountResponseVo;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageListResponseVo;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;

public interface ISuperMessageBusiness {
    PageResponse<SuperAppMessageListResponseVo> page(SuperMessagePageRequestVo request);

    Boolean read(SuperReadMessageRequestVo request);

    List<SuperAppMessageGroupCountResponseVo> groupCount(SuperAppMessageGroupCountRequestVo request);

    Integer getUnreadCount(SuperMessageUnreadCountRequestVo request);

    PageResponse<SuperAppMessageListResponseVo> customCategoryPage(DataMessageChannelEnum channelEnum, BaseEnum baseEnum, Long current, Long size);

}
