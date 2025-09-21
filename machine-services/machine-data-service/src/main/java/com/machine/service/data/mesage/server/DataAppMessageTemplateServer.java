package com.machine.service.data.mesage.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.message.IDataAppMessageTemplateClient;
import com.machine.client.data.message.dto.input.*;
import com.machine.client.data.message.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.mesage.service.IDataAppMessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/app_message_template")
public class DataAppMessageTemplateServer implements IDataAppMessageTemplateClient {

    @Autowired
    private IDataAppMessageTemplateService messageTemplateService;

    @Override
    @PostMapping("detail_by_type")
    public AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto inputDto) {
        log.info("通过模版类型查询消息模版，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return messageTemplateService.detailByType(inputDto);
    }

    @Override
    @PostMapping("page")
    public PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto inputDto) {
        log.info("分页查询消息模版列表，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return messageTemplateService.page(inputDto);
    }

    @Override
    @PostMapping("update_message_template")
    public void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto) {
        log.info("修改消息模版，inputDto={}", JSONUtil.toJsonStr(inputDto));
        messageTemplateService.updateMessageTemplate(inputDto);
    }

    @Override
    @PostMapping("update_message_template_status")
    public void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto) {
        log.info("修改消息模版状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        messageTemplateService.updateMessageTemplateStatus(inputDto);
    }

    @Override
    @PostMapping("update_message_template_channel")
    public void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto) {
        log.info("修改消息模版渠道，inputDto={}", JSONUtil.toJsonStr(inputDto));
        messageTemplateService.updateMessageTemplateChannel(inputDto);
    }

}
