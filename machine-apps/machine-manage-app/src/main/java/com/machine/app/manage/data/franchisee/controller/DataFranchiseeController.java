package com.machine.app.manage.data.franchisee.controller;

import com.machine.app.manage.data.franchisee.business.IDataFranchiseeBusiness;
import com.machine.app.manage.data.franchisee.controller.vo.response.FranchiseeDetailResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】加盟商模块")
@RestController
@RequestMapping("manage/data/franchisee")
public class DataFranchiseeController {

    @Autowired
    private IDataFranchiseeBusiness franchiseeBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    public FranchiseeDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return franchiseeBusiness.detail(request);
    }

}