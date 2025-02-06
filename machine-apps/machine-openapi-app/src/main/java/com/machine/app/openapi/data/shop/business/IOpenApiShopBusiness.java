package com.machine.app.openapi.data.shop.business;

import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;

import java.util.List;

public interface IOpenApiShopBusiness {

    String create(OpenApiShopCreateRequestVo request);

    void updateStatus(OpenApiShopUpdateShopStatusRequestVo request);

    void updateAddress(OpenApiShopUpdateAddressRequestVo request);

    void updateShopBusinessLicense(OpenApiShopUpdateShopBusinessLicenseRequestVo request);

    void updateFoodBusinessLicense(OpenApiShopUpdateFoodBusinessLicenseRequestVo request);

    void updateDisinfectingContract(OpenApiShopUpdateDisinfectingContractRequestVo request);

    void updateShopFrontPhoto(OpenApiShopUpdateShopFrontPhotoRequestVo request);

    void updateBase(OpenApiShopUpdateRequestVo request);

    String codeById(OpenApiShopIdRequestVo request);

    String idByCode(OpenApiShopCodeRequestVo request);

    OpenApiShopDetailResponseVo detail(OpenApiShopIdRequestVo request);

    OpenApiShopBusinessLicenseResponseVo getShopBusinessLicense(OpenApiShopIdRequestVo request);

    OpenApiFoodBusinessLicenseResponseVo getFoodBusinessLicense(OpenApiShopIdRequestVo request);

    OpenApiDisinfectingContractResponseVo getDisinfectingContract(OpenApiShopIdRequestVo request);

    OpenApiShopFrontPhotoResponseVo getShopFrontPhoto(OpenApiShopIdRequestVo request);

    List<String> queryShopLabelOptionIds(OpenApiShopIdRequestVo request);

    List<OpenApiShopListSimpleResponseVo> listSimple(OpenApiShopListSimpleRequestVo request);

}
