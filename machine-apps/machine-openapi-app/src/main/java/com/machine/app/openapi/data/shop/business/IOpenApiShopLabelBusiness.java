package com.machine.app.openapi.data.shop.business;

import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionListSimpleRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionIdRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionDetailResponseVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionListSimpleResponseVo;

import java.util.List;

public interface IOpenApiShopLabelBusiness {

    OpenApiShopLabelOptionDetailResponseVo detail(OpenApiShopLabelOptionIdRequestVo request);

    List<String> listShopIdByLabelOptionId(OpenApiShopLabelOptionIdRequestVo request);

    List<OpenApiShopLabelOptionListSimpleResponseVo> listSimple(OpenApiShopLabelOptionListSimpleRequestVo request);

}
