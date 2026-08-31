package com.machine.app.admin.hrm.department.controller;

import com.machine.app.admin.hrm.department.business.IHrmDepartmentBusiness;
import com.machine.app.admin.hrm.department.controller.vo.response.HrmDepartmentDetailResponseVo;
import com.machine.app.admin.hrm.department.controller.vo.response.HrmDepartmentExpandTreeResponseVo;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
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
@Tag(name = "【HRM】部门模块")
@RestController
@RequestMapping("admin/hrm/department")
public class HrmDepartmentController {

    @Autowired
    private IHrmDepartmentBusiness departmentBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.HRM,
            moduleEntity = ModuleEntityEnum.HRM_DEPARTMENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询部门详情")
    public HrmDepartmentDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return departmentBusiness.detail(request);
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @GetMapping("tree_all_simple")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.HRM,
            moduleEntity = ModuleEntityEnum.HRM_DEPARTMENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询部门树(组件弹窗)")
    public HrmDepartmentTreeOutputDto treeAllSimple() {
        return departmentBusiness.treeAllSimple();
    }

    @Operation(summary = "树(扩充，应用于部门管理菜单)")
    @GetMapping("tree_all_expand")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.HRM,
            moduleEntity = ModuleEntityEnum.HRM_DEPARTMENT,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询部门树(部门管理菜单)")
    public HrmDepartmentExpandTreeResponseVo treeExpand() {
        return departmentBusiness.treeAllExpand();
    }

}