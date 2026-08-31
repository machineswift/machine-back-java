package com.machine.app.iam.biam.log.controller;

import com.machine.app.iam.biam.log.business.IBIamUserLoginLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogExpandListResponseVo;
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
@Tag(name = "【BIAM】用户登录日志模块")
@RestController
@RequestMapping("iam/biam/user_login_log")
public class BIamUserLoginLogController {

    @Autowired
    private IBIamUserLoginLogBusiness userLoginLogBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:LOGIN_LOG:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER_LOGIN_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询登录日志详情")
    public BIamUserLoginLogDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return userLoginLogBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:LOG_CENTER:LOGIN_LOG:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER_LOGIN_LOG,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询登录日志")
    public PageResponse<BIamUserLoginLogExpandListResponseVo> pageExpand(@RequestBody @Validated BIamUserLoginLogQueryPageRequestVo request) {
        return userLoginLogBusiness.pageExpand(request);
    }
}