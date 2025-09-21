package com.machine.app.openapi.data.employee.controller;

import com.machine.app.openapi.data.employee.business.IOpenApiCompanyEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeIdRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeListSimpleRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiCompanyEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiCompanyEmployeeDetailResponseVo;
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
@Tag(name = "【DATA】公司员工模块")
@RestController
@RequestMapping("openapi/data/company_employee")
public class OpenApiCompanyEmployeeController {

    @Autowired
    private IOpenApiCompanyEmployeeBusiness companyEmployeeBusiness;

    @Operation(summary = "公司员工详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiCompanyEmployeeDetailResponseVo detail(@RequestBody @Valid OpenApiCompanyEmployeeIdRequestVo request) {
        return companyEmployeeBusiness.detail(request);
    }

    @Operation(summary = "获取公司员工列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public List<OpenApiCompanyEmployeeListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiCompanyEmployeeListSimpleRequestVo request) {
        return companyEmployeeBusiness.listSimple(request);
    }

}