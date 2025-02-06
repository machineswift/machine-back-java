package com.machine.app.openapi.data.employee.business;

import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeIdRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeListSimpleRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiCompanyEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiCompanyEmployeeDetailResponseVo;

import java.util.List;

public interface IOpenApiCompanyEmployeeBusiness {

    OpenapiCompanyEmployeeDetailResponseVo detail(OpenApiCompanyEmployeeIdRequestVo request);

    List<OpenApiCompanyEmployeeListSimpleResponseVo> listSimple(OpenApiCompanyEmployeeListSimpleRequestVo request);
}
