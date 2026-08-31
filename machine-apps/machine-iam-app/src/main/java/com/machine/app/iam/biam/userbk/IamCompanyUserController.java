package com.machine.app.iam.biam.userbk;

import com.machine.app.iam.biam.userbk.business.IIamCompanyUserBusiness;
import com.machine.app.iam.biam.userbk.vo.request.IamCompanyUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【BIAM】用户模块")
@RestController
@RequestMapping("iam/biam/user_company")
public class IamCompanyUserController {

    @Autowired
    private IIamCompanyUserBusiness companyUserBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询企业用户详情")
    public IamCompanyUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return companyUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询企业用户(员工管理菜单)")
    public PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(
            @RequestBody @Validated IamCompanyUserQueryPageExpandRequestVo request) {
        return companyUserBusiness.pageExpand(request);
    }
}