package com.machine.app.iam.biam.log.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.log.business.IBIamUserAccessLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogDeleteRequestVo;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogExpandListResponseVo;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
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
@Tag(name = "【BIAM】用户访问日志模块")
@RestController
@RequestMapping("iam/biam/user_access_log")
public class BIamUserAccessLogController {

    @Autowired
    private IBIamUserAccessLogBusiness userAccessLogBusiness;

    @Operation(summary = "清理访问日志")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:ACCESS_LOG:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER_ACCESS_LOG,
            operateType = ActionTypeEnum.DELETE,
            operateName = "清理访问日志",
            moduleEntityId = "''",
            content = "'清理访问日志：' + #request.beforeCreateTime",
            diff = false)
    public int delete(@RequestBody @Validated BIamUserAccessLogDeleteRequestVo request) {
        log.info("清理指定时间之前的日志,request:{}", JSONUtil.toJsonStr(request));
        return userAccessLogBusiness.delete(request);
    }

    @Operation(summary = "查询访问日志详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:ACCESS_LOG:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER_ACCESS_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询访问日志详情")
    public BIamUserAccessLogDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return userAccessLogBusiness.detail(request);
    }

    @Operation(summary = "分页查询访问日志")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:ACCESS_LOG:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER_ACCESS_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询访问日志")
    public PageResponse<BIamUserAccessLogExpandListResponseVo> pageExpand(
            @RequestBody @Validated BIamUserAccessLogQueryPageRequestVo request) {
        return userAccessLogBusiness.pageExpand(request);
    }

}
