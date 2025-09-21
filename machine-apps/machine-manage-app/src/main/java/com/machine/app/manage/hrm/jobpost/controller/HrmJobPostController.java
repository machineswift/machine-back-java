package com.machine.app.manage.hrm.jobpost.controller;

import com.machine.app.manage.hrm.jobpost.business.IHrmJobPostBusiness;
import com.machine.app.manage.hrm.jobpost.controller.vo.request.HrmJobPostListSimpleRequestVo;
import com.machine.app.manage.hrm.jobpost.controller.vo.response.HrmJobPostListSimpleResponseVo;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【HRM】职务模块")
@RestController
@RequestMapping("manage/hrm/job_post")
public class HrmJobPostController {

    @Autowired
    private IHrmJobPostBusiness jobPostBusiness;

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simple")
    public PageResponse<HrmJobPostListSimpleResponseVo> pageSimple(@RequestBody @Validated HrmJobPostListSimpleRequestVo request) {
        return jobPostBusiness.pageSimple(request);
    }

}