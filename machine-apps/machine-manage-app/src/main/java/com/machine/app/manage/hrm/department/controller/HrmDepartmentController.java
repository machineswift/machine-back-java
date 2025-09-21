package com.machine.app.manage.hrm.department.controller;

import com.machine.app.manage.hrm.department.business.IHrmDepartmentBusiness;
import com.machine.app.manage.hrm.department.controller.vo.response.*;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【HRM】部门模块")
@RestController
@RequestMapping("manage/hrm/department")
public class HrmDepartmentController {

    @Autowired
    private IHrmDepartmentBusiness departmentBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    public HrmDepartmentDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return departmentBusiness.detail(request);
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @GetMapping("tree_all_simple")
    public HrmDepartmentTreeOutputDto treeAllSimple() {
        return departmentBusiness.treeAllSimple();
    }

    @Operation(summary = "树(扩充，应用于部门管理菜单)")
    @GetMapping("tree_all_expand")
    public HrmDepartmentExpandTreeResponseVo treeExpand() {
        return departmentBusiness.treeAllExpand();
    }

}