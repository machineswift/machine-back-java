package com.machine.app.manage.data.message.controller;

import com.machine.app.manage.data.message.business.IManageAppMessageBusiness;
import com.machine.app.manage.data.message.controller.vo.request.ManageAppMessagePageReqVo;
import com.machine.app.manage.data.message.controller.vo.request.ManageReadMessageRequestVo;
import com.machine.app.manage.data.message.controller.vo.response.ManageAppMessageListResVo;
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
@Tag(name = "【DATA】消息模块")
@RestController
@RequestMapping("manage/data/message")
public class ManageAppMessageController {

    @Autowired
    private IManageAppMessageBusiness messageBusiness;

    @Operation(summary = "分页查询消息")
    @PostMapping("page_message")
    PageResponse<ManageAppMessageListResVo> pageMessage(@RequestBody ManageAppMessagePageReqVo request){
        return messageBusiness.getMessagePage(request);
    }

    @Operation(summary = "消息改成已读")
    @PostMapping("read")
    Boolean readMessage(@RequestBody @Validated ManageReadMessageRequestVo request){
        return messageBusiness.read(request);
    }

}