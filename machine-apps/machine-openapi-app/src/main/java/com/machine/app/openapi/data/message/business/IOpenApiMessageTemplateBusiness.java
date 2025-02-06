package com.machine.app.openapi.data.message.business;

import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageTemplateDetailByTypeRequestVo;
import com.machine.app.openapi.data.message.controller.vo.response.OpenApiMessageTemplateDetailResponseVo;

public interface IOpenApiMessageTemplateBusiness {

    OpenApiMessageTemplateDetailResponseVo detailByType(OpenApiMessageTemplateDetailByTypeRequestVo request);
}
