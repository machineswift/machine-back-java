package com.machine.app.openapi.data.shop.business;

import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;

import java.util.List;
import java.util.Map;

public interface IOpenApiShopBusiness {

    String idByCode(OpenApiShopCodeRequestVo request);

    Map<String, String> idByCodeSet(OpenApiShopCodeSetRequestVo request);

    OpenApiShopDetailResponseVo detail(OpenApiShopIdRequestVo request);

    List<OpenApiShopListSimpleResponseVo> listSimple(OpenApiShopListSimpleRequestVo request);

}
