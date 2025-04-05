package com.machine.app.openapi.data.franchisee.controller;

import com.machine.app.openapi.data.franchisee.business.IOpenApiFranchiseeBusiness;
import com.machine.app.openapi.data.franchisee.controller.vo.request.*;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeDetailResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeListSampleResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeHealthCertificateResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeIdentityCardResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "【DATA】加盟商模块")
@RestController
@RequestMapping("openapi/data/franchisee")
public class OpenApiFranchiseeController {

    @Autowired
    private IOpenApiFranchiseeBusiness franchiseeBusiness;

    @Operation(summary = "获取加盟商详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public OpenApiFranchiseeDetailResponseVo detail(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.detail(request);
    }

    @Operation(summary = "获取身份证")
    @PostMapping("identity_card")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiFranchiseeIdentityCardResponseVo identityCard(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.identityCard(request);
    }

    @Operation(summary = "获取健康证")
    @PostMapping("health_certificate")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiFranchiseeHealthCertificateResponseVo healthCertificate(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.healthCertificate(request);
    }

    @Operation(summary = "获取加盟商列表(基础信息)")
    @PostMapping("list_sample")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public List<OpenApiFranchiseeListSampleResponseVo> listSample(@RequestBody @Valid OpenApiFranchiseeListSampleRequestVo request) {
        return franchiseeBusiness.listSample(request);
    }

}