package com.machine.app.openapi.data.message.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.message.business.IOpenApiMessageBusiness;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageSaveRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiReadMessageRequestVo;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiSendMessageRequestVo;
import com.machine.sdk.common.context.AppContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】消息模块")
@RestController
@RequestMapping("openapi/data/message")
public class OpenApiMessageController {

    @Autowired
    private IOpenApiMessageBusiness messageBusiness;

    @Operation(summary = "新增消息")
    @PostMapping("save")
    @PreAuthorize("hasAnyAuthority('data','data_message')")
    Boolean saveMessageRecord(@RequestBody @Validated OpenApiMessageSaveRequestVo request) {
        log.info("新增消息，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return messageBusiness.save(request);
    }

    @Operation(summary = "发送消息")
    @PostMapping("send")
    @PreAuthorize("hasAnyAuthority('data','data_message')")
    Boolean sendMessage(@RequestBody @Validated OpenApiSendMessageRequestVo request) {
        log.info("发送消息，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return messageBusiness.send(request);
    }


    @Operation(summary = "消息改成已读")
    @PostMapping("read")
    @PreAuthorize("hasAnyAuthority('data','data_message')")
    Boolean readMessage(@RequestBody @Validated OpenApiReadMessageRequestVo request) {
        log.info("消息改成已读，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return messageBusiness.read(request);
    }

    @Operation(summary = "消息改成已处理")
    @PostMapping("dispose")
    @PreAuthorize("hasAnyAuthority('data','data_message')")
    Boolean disposeMessage(@RequestBody @Validated OpenApiReadMessageRequestVo request) {
        log.info("消息改成已处理，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return messageBusiness.dispose(request);
    }

}