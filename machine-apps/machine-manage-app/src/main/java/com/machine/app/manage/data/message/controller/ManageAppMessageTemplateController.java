package com.machine.app.manage.data.message.controller;

import com.machine.app.manage.data.message.business.IManageAppMessageTemplateBusiness;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateListReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateChannelReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessageTemplateUpdateStatusReqVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageTemplateListResVo;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】消息模版模块")
@RestController
@RequestMapping("manage/data/message_template")
public class ManageAppMessageTemplateController {

    @Autowired
    private IManageAppMessageTemplateBusiness messageTemplateBusiness;

    @Operation(summary = "获取消息模版")
    @PostMapping("get_message_template_page")
    PageResponse<ManageAppMessageTemplateListResVo> getMessageTemplatePage(@RequestBody @Validated ManageAppMessageTemplateListReqVo request){
        return messageTemplateBusiness.getMessageTemplatePage(request);
    }

    @Operation(summary = "编辑消息模版")
    @PostMapping("update_message_template")
    void updateMessageTemplate(@RequestBody @Validated ManageAppMessageTemplateUpdateReqVo request){
        messageTemplateBusiness.updateMessageTemplate(request);
    }

    @Operation(summary = "编辑消息模版状态")
    @PostMapping("update_message_template_status")
    void updateMessageTemplateStatus(@RequestBody @Validated ManageAppMessageTemplateUpdateStatusReqVo request){
        messageTemplateBusiness.updateMessageTemplateStatus(request);
    }

    @Operation(summary = "编辑消息模版推送渠道")
    @PostMapping("update_message_template_channel")
    void updateMessageTemplateChannel(@RequestBody @Validated ManageAppMessageTemplateUpdateChannelReqVo request){
        messageTemplateBusiness.updateMessageTemplateChannel(request);
    }

}