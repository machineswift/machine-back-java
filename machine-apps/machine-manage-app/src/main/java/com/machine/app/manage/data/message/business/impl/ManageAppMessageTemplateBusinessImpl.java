package com.machine.app.manage.data.message.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.message.business.IManageAppMessageTemplateBusiness;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateListReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateChannelReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateStatusReqVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageTemplateListResVo;
import com.machine.client.data.message.IDataAppMessageTemplateClient;
import com.machine.client.data.message.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateChannelInput;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateStatusInputDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManageAppMessageTemplateBusinessImpl implements IManageAppMessageTemplateBusiness {

    @Autowired
    private IDataAppMessageTemplateClient messageTemplateClient;
    @Override
    public PageResponse<ManageAppMessageTemplateListResVo> getMessageTemplatePage(ManageAppMessageTemplateListReqVo request) {
        AppMessageTemplateQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageTemplateQueryPageInputDto.class);

        PageResponse<AppMessageTemplateListOutPutDto> page = messageTemplateClient.page(inputDto);
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), ManageAppMessageTemplateListResVo.class));
    }

    @Override
    public void updateMessageTemplate(ManageAppMessageTemplateUpdateReqVo request) {
        AppMessageTemplateUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageTemplateUpdateInputDto.class);
        messageTemplateClient.updateMessageTemplate(inputDto);
    }

    @Override
    public void updateMessageTemplateStatus(ManageAppMessageTemplateUpdateStatusReqVo request) {
        AppMessageTemplateUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageTemplateUpdateStatusInputDto.class);
        messageTemplateClient.updateMessageTemplateStatus(inputDto);
    }

    @Override
    public void updateMessageTemplateChannel(ManageAppMessageTemplateUpdateChannelReqVo request) {
        AppMessageTemplateUpdateChannelInput inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageTemplateUpdateChannelInput.class);
        messageTemplateClient.updateMessageTemplateChannel(inputDto);
    }
}
