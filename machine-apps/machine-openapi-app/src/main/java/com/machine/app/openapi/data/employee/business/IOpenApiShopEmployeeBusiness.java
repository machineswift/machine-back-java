package com.machine.app.openapi.data.employee.business;

import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;

import java.util.List;

public interface IOpenApiShopEmployeeBusiness {

    OpenapiShopEmployeeDetailResponseVo detail(OpenApiShopEmployeeIdRequestVo request);

    OpenapiShopEmployeeIdentityCardResponseVo identityCard(OpenApiShopEmployeeIdRequestVo request);

    OpenapiShopEmployeeHealthCertificateResponseVo healthCertificate(OpenApiShopEmployeeIdRequestVo request);

    List<OpenApiShopEmployeeListSimpleResponseVo> listSimple(OpenApiShopEmployeeListSimpleRequestVo request);
}
