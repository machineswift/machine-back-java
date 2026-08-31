package com.machine.app.iam.biam.log.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.log.business.IBIamOperationLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamOperationLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogExpandListResponseVo;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
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
@Tag(name = "【BIAM】操作日志模块")
@RestController
@RequestMapping("iam/biam/operation_log")
public class BIamOperationLogController {

    @Autowired
    private IBIamOperationLogBusiness operationLogBusiness;

    @Operation(summary = "查询操作日志详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:OPERATION_LOG:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_OPERATION_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询操作日志详情")
    public BIamOperationLogDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        log.info("查询操作日志详情,request:{}", JSONUtil.toJsonStr(request));
        return operationLogBusiness.detail(request);
    }

    @Operation(summary = "分页查询操作日志")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:OPERATION_LOG:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_OPERATION_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询操作日志")
    public PageResponse<BIamOperationLogExpandListResponseVo> pageExpand(
            @RequestBody @Validated BIamOperationLogQueryPageRequestVo request) {
        return operationLogBusiness.pageExpand(request);
    }

}
