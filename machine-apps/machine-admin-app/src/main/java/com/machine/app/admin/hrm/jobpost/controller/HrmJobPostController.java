package com.machine.app.admin.hrm.jobpost.controller;

import com.machine.app.admin.hrm.jobpost.business.IHrmJobPostBusiness;
import com.machine.app.admin.hrm.jobpost.controller.vo.request.HrmJobPostListSimpleRequestVo;
import com.machine.app.admin.hrm.jobpost.controller.vo.response.HrmJobPostListSimpleResponseVo;
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
@Tag(name = "【HRM】职务模块")
@RestController
@RequestMapping("admin/hrm/job_post")
public class HrmJobPostController {

    @Autowired
    private IHrmJobPostBusiness jobPostBusiness;

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simple")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.HRM,
            moduleEntity = ModuleEntityEnum.HRM_JOB_POST,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询职务(组件弹窗)")
    public PageResponse<HrmJobPostListSimpleResponseVo> pageSimple(@RequestBody @Validated HrmJobPostListSimpleRequestVo request) {
        return jobPostBusiness.pageSimple(request);
    }

}