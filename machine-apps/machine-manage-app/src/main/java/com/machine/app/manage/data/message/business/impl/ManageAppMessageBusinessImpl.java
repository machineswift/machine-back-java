package com.machine.app.manage.data.message.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.message.business.IManageAppMessageBusiness;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessagePageReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageReadMessageRequestVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageListResVo;
import com.machine.client.data.message.IDataAppMessageClient;
import com.machine.client.data.message.dto.input.AppMessagePageInputDto;
import com.machine.client.data.message.dto.input.AppMessageReadInputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManageAppMessageBusinessImpl implements IManageAppMessageBusiness {

    @Autowired
    private IDataAppMessageClient appMessageClient;

    @Override
    public PageResponse<ManageAppMessageListResVo> getMessagePage(ManageAppMessagePageReqVo request) {
        AppMessagePageInputDto appMessagePageInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessagePageInputDto.class);
        PageResponse<AppMessageListOutputDto> page = appMessageClient.managePage(appMessagePageInputDto);
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), ManageAppMessageListResVo.class));
    }

    @Override
    public Boolean read(ManageReadMessageRequestVo request) {
        AppMessageReadInputDto messageReadInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AppMessageReadInputDto.class);
        return appMessageClient.readMessage(messageReadInputDto);
    }
}
