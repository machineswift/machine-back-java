package com.machine.service.data.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IShopService {

    String create(DataShopCreateInputDto inputDto);

    int updateLabel(ShopUpdateShopLabelOptionInputDto inputDto);

    int batchUpdateLabel(ShopBatchUpdateShopLabelOptionInputDto inputDto);

    int updateStatus(ShopUpdateShopStatusInputDto inputDto);

    int updateOpeningDate(ShopUpdateOpeningDateInputDto inputDto);

    int updateAddress(OpenApiShopUpdateAddressInputDto inputDto);

    int updateShopBusinessLicense(OpenApiShopUpdateShopBusinessLicenseInputDto inputDto);

    int updateFoodBusinessLicense(OpenApiShopUpdateFoodBusinessLicenseInputDto inputDto);

    int updateDisinfectingContract(OpenApiShopUpdateDisinfectingContractInputDto inputDto);

    int updateShopFrontPhoto(OpenApiShopUpdateShopFrontPhotoInputDto inputDto);

    int updateBaseInfo(DataShopUpdateShopBaseInfoInputDto inputDto);

    int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    AddressInfoDto getAddressInfo(IdRequest request);

    ShopBusinessLicenseOutputDto getShopBusinessLicense(IdRequest request);

    FoodBusinessLicenseOutputDto getFoodBusinessLicense(IdRequest request);

    OpenApiDisinfectingContractOutputDto getDisinfectingContract(IdRequest request);

    OpenApiShopFrontPhotoOutputDto getShopFrontPhoto(IdRequest request);

    DataShopDetailOutputDto getById(IdRequest request);

    DataShopDetailOutputDto getByCode(DataShopCodeInoutDto inoutDto);

    List<String> listAreaCodeByShopIdSet(IdSetRequest request);

    List<String> listShopIdByShopCodeSet(IdSetRequest request);

    List<String> listShopIdByAreaCodeSet(IdSetRequest request);

    List<DataShopDetailOutputDto> listByIdSet(IdSetRequest request);

    List<DataShopDetailOutputDto> listByShopCodeSet(IdSetRequest request);

    Map<String, DataShopDetailOutputDto> mapByIdSet(IdSetRequest request);

    List<DataShopListOutputDto> listByOffset(DataShopQueryListOffsetInputDto inputDto);

    List<DataShopListOutputDto> listAll(DataShopQueryListAllInputDto inputDto);

    Page<DataShopListOutputDto> pageShop(DataShopQueryPageInputDto inputDto);

    Map<String, AddressInfoDto> mapAddressInfo(IdSetRequest request);

}
