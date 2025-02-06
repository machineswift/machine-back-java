package com.machine.app.openapi.data.message.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.message.business.IOpenApiMessageTemplateBusiness;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageTemplateDetailByTypeRequestVo;
import com.machine.app.openapi.data.message.controller.vo.response.OpenApiMessageTemplateDetailResponseVo;
import com.machine.client.data.informaion.input.AppMessageReadInputDto;
import com.machine.client.data.messageTemplate.IDataAppMessageTemplateClient;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateDetailOutputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenApiMessageTemplateBusinessImpl implements IOpenApiMessageTemplateBusiness {

    @Autowired
    private IDataAppMessageTemplateClient messageTemplateClient;

    @Override
    public OpenApiMessageTemplateDetailResponseVo detailByType(OpenApiMessageTemplateDetailByTypeRequestVo request) {
        AppMessageTemplateDetailByTypeInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageTemplateDetailByTypeInputDto.class);
        AppMessageTemplateDetailOutputDto outputDto = messageTemplateClient.detailByType(inputDto);
        if(outputDto == null){
            return new OpenApiMessageTemplateDetailResponseVo();
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiMessageTemplateDetailResponseVo.class);
    }
}
