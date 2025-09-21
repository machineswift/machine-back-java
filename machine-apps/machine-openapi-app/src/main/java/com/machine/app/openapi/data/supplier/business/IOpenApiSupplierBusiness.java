package com.machine.app.openapi.data.supplier.business;

import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierListSimpleResponseVo;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;

import java.util.List;

public interface IOpenApiSupplierBusiness {

    OpenApiSupplierDetailResponseVo detail(OpenApiSupplierIdRequestVo request);

    OpenApiSupplierDetailResponseVo detailByUserId(OpenApiUserIdRequestVo request);

    List<OpenApiSupplierListSimpleResponseVo> listSimple(OpenApiSupplierListSimpleRequestVo request);

}
