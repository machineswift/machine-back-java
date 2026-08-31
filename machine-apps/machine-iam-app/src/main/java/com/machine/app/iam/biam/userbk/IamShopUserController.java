package com.machine.app.iam.biam.userbk;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.userbk.business.IIamShopUserBusiness;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserCreateRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamShopUserUpdateRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamShopUserDetailResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
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
@Tag(name = "【BIAM】门店员工模块")
@RestController
@RequestMapping("iam/biam/user_shop")
public class IamShopUserController {

    @Autowired
    private IIamShopUserBusiness shopUserBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建门店员工")
    public IdResponse<String> create(@RequestBody @Validated IamShopUserCreateRequestVo request) {
        log.info("创建门店员工，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopUserBusiness.create(request));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改门店员工")
    public void update(@RequestBody @Validated IamShopUserUpdateRequestVo request) {
        log.info("修改门店员工，request={}", JSONUtil.toJsonStr(request));
        shopUserBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询门店员工详情")
    public IamShopUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return shopUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询门店员工(员工管理菜单)")
    public PageResponse<IamShopUserExpandListResponseVo> pageExpand(
            @RequestBody @Validated IamShopUserQueryPageExpandRequestVo request) {
        return shopUserBusiness.pageExpand(request);
    }

}