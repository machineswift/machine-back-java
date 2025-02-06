package com.machine.app.openapi.data.message.business;

import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageSaveRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiReadMessageRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiSendMessageRequestVo;

public interface IOpenApiMessageBusiness {

    Boolean save(OpenApiMessageSaveRequestVo request);

    Boolean send(OpenApiSendMessageRequestVo request);

    Boolean read(OpenApiReadMessageRequestVo request);

    Boolean dispose(OpenApiReadMessageRequestVo request);
}
