package com.machine.app.admin.data.franchisee.controller;

import com.machine.app.admin.data.franchisee.business.IDataFranchiseeBusiness;
import com.machine.app.admin.data.franchisee.controller.vo.response.FranchiseeDetailResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
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
@Tag(name = "【DATA】加盟商模块")
@RestController
@RequestMapping("admin/data/franchisee")
public class DataFranchiseeController {

    @Autowired
    private IDataFranchiseeBusiness franchiseeBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.DATA,
            moduleEntity = ModuleEntityEnum.DATA_FRANCHISEE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询加盟商详情")
    public FranchiseeDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return franchiseeBusiness.detail(request);
    }

}