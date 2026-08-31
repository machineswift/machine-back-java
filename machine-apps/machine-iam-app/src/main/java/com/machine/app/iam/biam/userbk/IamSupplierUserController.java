package com.machine.app.iam.biam.userbk;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.userbk.business.IIamSupplierUserBusiness;
import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamSupplierUserDetailResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamSupplierUserExpandListResponseVo;
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
@Tag(name = "【BIAM】供应商模块")
@RestController
@RequestMapping("iam/biam/user_supplier")
public class IamSupplierUserController {

    @Autowired
    private IIamSupplierUserBusiness supplierUserBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建供应商")
    public IdResponse<String> create(@RequestBody @Validated IamSupplierUserCreateRequestVo request) {
        log.info("创建供应商，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(supplierUserBusiness.create(request));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改供应商")
    public void update(@RequestBody @Validated IamSupplierUserUpdateRequestVo request) {
        log.info("修改供应商，request={}", JSONUtil.toJsonStr(request));
        supplierUserBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询供应商详情")
    public IamSupplierUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return supplierUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询供应商(员工管理菜单)")
    public PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(
            @RequestBody @Validated IamSupplierUserQueryPageExpandRequestVo request) {
        return supplierUserBusiness.pageExpand(request);
    }

}