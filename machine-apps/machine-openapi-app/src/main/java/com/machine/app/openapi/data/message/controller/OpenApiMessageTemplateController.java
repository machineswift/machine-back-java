package com.machine.app.openapi.data.message.controller;

import com.machine.app.openapi.data.message.business.IOpenApiMessageTemplateBusiness;
import com.machine.app.openapi.data.message.controller.vo.request.OpenApiMessageTemplateDetailByTypeRequestVo;
import com.machine.app.openapi.data.message.controller.vo.response.OpenApiMessageTemplateDetailResponseVo;
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
@Tag(name = "【DATA】消息模版模块")
@RestController
@RequestMapping("openapi/data/message_template")
public class OpenApiMessageTemplateController {

    @Autowired
    private IOpenApiMessageTemplateBusiness messageTemplateBusiness;

    @Operation(summary = "根据类型获取消息模版")
    @PostMapping("detail_by_type")
    @PreAuthorize("hasAnyAuthority('data','data_message')")
    OpenApiMessageTemplateDetailResponseVo detailByType(@RequestBody @Validated OpenApiMessageTemplateDetailByTypeRequestVo request) {
        return messageTemplateBusiness.detailByType(request);
    }

}