package com.machine.app.openapi.data.employee.business;

import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;

import java.util.List;

public interface IOpenApiShopEmployeeBusiness {

    String create(OpenapiShopEmployeeCreateRequestVo request);

    void updateStatus(OpenapiShopEmployeeUpdateStatusRequestVo request);

    void updatePhone(OpenapiShopEmployeeUpdatePhoneRequestVo request);

    void updateRole(OpenApiShopEmployeeUpdateRoleRequestVo request);

    void updateIdentityCard(OpenApiShopEmployeeUpdateIdentityCardRequestVo request);

    void updateHealthCertificate(OpenApiShopEmployeeUpdateHealthCertificateRequestVo request);

    OpenapiShopEmployeeDetailResponseVo detail(OpenApiShopEmployeeIdRequestVo request);

    OpenapiShopEmployeeIdentityCardResponseVo identityCard(OpenApiShopEmployeeIdRequestVo request);

    OpenapiShopEmployeeHealthCertificateResponseVo healthCertificate(OpenApiShopEmployeeIdRequestVo request);

    List<OpenApiShopEmployeeListSimpleResponseVo> listSimple(OpenApiShopEmployeeListSimpleRequestVo request);
}
