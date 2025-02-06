package com.machine.app.openapi.data.supplier.business;

import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyListSimpleResponseVo;

import java.util.List;

public interface IOpenApiSupplierCompanyBusiness {

    OpenApiSupplierCompanyDetailResponseVo detail(OpenApiSupplierCompanyIdRequestVo request);

    List<OpenApiSupplierCompanyListSimpleResponseVo> listSimple(OpenApiSupplierCompanyListSimpleRequestVo request);

}
