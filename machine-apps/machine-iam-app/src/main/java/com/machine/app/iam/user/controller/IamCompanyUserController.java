package com.machine.app.iam.user.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamCompanyUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】公司用户模块")
@RestController
@RequestMapping("iam/user_company")
public class IamCompanyUserController {

    @Autowired
    private IIamCompanyUserBusiness companyUserBusiness;

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamCompanyUserUpdateRequestVo request) {
        log.info("修改公司员工，request={}", JSONUtil.toJsonStr(request));
        companyUserBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamCompanyUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return companyUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(@RequestBody @Validated IamCompanyUserQueryPageExpandRequestVo request) {
        return companyUserBusiness.pageExpand(request);
    }
}