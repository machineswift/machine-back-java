package com.machine.app.openapi.data.message.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.message.business.IOpenApiMessageBusiness;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageSaveRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiReadMessageRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiSendMessageRequestVo;
import com.machine.client.data.message.IDataAppMessageClient;
import com.machine.client.data.message.dto.input.AppMessageReadInputDto;
import com.machine.client.data.message.dto.input.AppMessageSaveInputDto;
import com.machine.client.data.message.dto.input.AppMessageSendInputDto;
import com.machine.starter.mq.function.CustomerStreamBridge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenApiMessageBusinessImpl implements IOpenApiMessageBusiness {

    @Autowired
    private IDataAppMessageClient messageClient;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Override
    public Boolean save(OpenApiMessageSaveRequestVo request) {
        AppMessageSaveInputDto messageSaveInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageSaveInputDto.class);
        return messageClient.saveMessageRecord(messageSaveInputDto);
    }

    @Override
    public Boolean send(OpenApiSendMessageRequestVo request) {
        AppMessageSendInputDto messageSendInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageSendInputDto.class);
        customerStreamBridge.send("businessMessageProducer", messageSendInputDto);
        return true;
    }

    @Override
    public Boolean read(OpenApiReadMessageRequestVo request) {
        AppMessageReadInputDto messageReadInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageReadInputDto.class);
        return messageClient.readMessage(messageReadInputDto);
    }

    @Override
    public Boolean dispose(OpenApiReadMessageRequestVo request) {
        AppMessageReadInputDto messageReadInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageReadInputDto.class);
        return messageClient.disposeMessage(messageReadInputDto);
    }
}
