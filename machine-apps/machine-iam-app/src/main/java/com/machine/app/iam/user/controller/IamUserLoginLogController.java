package com.machine.app.iam.user.controller;

import com.machine.app.iam.user.business.IIamUserLoginLogBusiness;
import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogPageResponseVo;
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
@Tag(name = "【IAM】用户登录日志模块")
@RestController
@RequestMapping("iam/user_login_log")
public class IamUserLoginLogController {

    @Autowired
    private IIamUserLoginLogBusiness userLoginLogBusiness;

    @Operation(summary = "分页查询")
    @PostMapping("page")
    public PageResponse<IamUserLoginLogPageResponseVo> page(@RequestBody @Validated IamUserLoginLogQueryPageRequestVo request) {
        return userLoginLogBusiness.page(request);
    }
}