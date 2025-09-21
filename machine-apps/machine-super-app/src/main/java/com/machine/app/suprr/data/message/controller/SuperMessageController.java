package com.machine.app.suprr.data.message.controller;

import com.machine.app.suprr.data.message.business.ISuperMessageBusiness;
import com.machine.app.suprr.data.message.controller.vo.request.*;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageGroupCountResponseVo;
import com.machine.app.suprr.data.message.controller.vo.response.SuperAppMessageListResponseVo;
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

import java.util.List;

@Slf4j
@Tag(name = "【DATA】消息模块")
@RestController
@RequestMapping("super/data/message")
public class SuperMessageController {

    @Autowired
    private ISuperMessageBusiness messageBusiness;

    @Operation(summary = "分页查询")
    @PostMapping("page")
    PageResponse<SuperAppMessageListResponseVo> page(@RequestBody @Validated SuperMessagePageRequestVo request){
        return messageBusiness.page(request);
    }

    @Operation(summary = "风险管理消息分页查询")
    @PostMapping("risk_page")
    PageResponse<SuperAppMessageListResponseVo> riskPage(@RequestBody @Validated SuperMessageRiskCategoryPageRequestVo request){
        return messageBusiness.customCategoryPage(request.getChannel(),request.getRiskCategory(),request.getCurrent(),request.getSize());
    }

    @Operation(summary = "任务管理消息分页查询")
    @PostMapping("task_page")
    PageResponse<SuperAppMessageListResponseVo> taskPage(@RequestBody @Validated SuperMessageTaskCategoryPageRequestVo request){
        return messageBusiness.customCategoryPage(request.getChannel(),request.getTaskCategory(),request.getCurrent(),request.getSize());
    }


    @Operation(summary = "消息改成已读")
    @PostMapping("read")
    Boolean readMessage(@RequestBody @Validated SuperReadMessageRequestVo request){
        return messageBusiness.read(request);
    }

    @Operation(summary = "消息分类数量统计")
    @PostMapping("group_count")
    List<SuperAppMessageGroupCountResponseVo> groupCount(@RequestBody @Validated SuperAppMessageGroupCountRequestVo request){
        return messageBusiness.groupCount(request);
    }

    @Operation(summary = "查询当前用户未读信息的数量")
    @PostMapping("get_unread_count")
    Integer getUnreadCount(@RequestBody SuperMessageUnreadCountRequestVo request){
        return messageBusiness.getUnreadCount(request);
    }
}