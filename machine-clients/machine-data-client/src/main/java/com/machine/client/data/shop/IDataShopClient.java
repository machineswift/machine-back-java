package com.machine.client.data.shop;

import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/shop", configuration = OpenFeignDefaultConfig.class)
public interface IDataShopClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataShopCreateInputDto inputDto);

    @PostMapping("update_label_option")
    int updateLabelOption(@RequestBody @Validated ShopUpdateShopLabelOptionInputDto inputDto);

    @PostMapping("batch_update_label_option")
    int batchUpdateLabelOption(@RequestBody @Validated ShopBatchUpdateShopLabelOptionInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated ShopUpdateShopStatusInputDto inputDto);

    @PostMapping("update_opening_date")
    int updateOpeningDate(@RequestBody @Validated ShopUpdateOpeningDateInputDto inputDto);

    @PostMapping("update_address")
    int updateAddress(@RequestBody @Validated OpenApiShopUpdateAddressInputDto inputDto);

    @PostMapping("update_shop_business_license")
    int updateShopBusinessLicense(@RequestBody @Validated OpenApiShopUpdateShopBusinessLicenseInputDto inputDto);

    @PostMapping("update_food_business_license")
    int updateFoodBusinessLicense(@RequestBody @Validated OpenApiShopUpdateFoodBusinessLicenseInputDto inputDto);

    @PostMapping("update_disinfecting_contract")
    int updateDisinfectingContract(@RequestBody @Validated OpenApiShopUpdateDisinfectingContractInputDto inputDto);

    @PostMapping("update_shop_front_photo")
    int updateShopFrontPhoto(@RequestBody @Validated OpenApiShopUpdateShopFrontPhotoInputDto inputDto);

    @PostMapping("update_base_info")
    int updateBaseInfo(@RequestBody @Validated DataShopUpdateShopBaseInfoInputDto inputDto);

    @PostMapping("count_not_bind_Organization")
    int countNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto);

    @PostMapping("list_not_bind_Organization")
    List<String> listNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto);

    @PostMapping("get_addressInfo")
    AddressInfoDto getAddressInfo(@RequestBody @Validated IdRequest request);

    @PostMapping("get_shop_business_license")
    ShopBusinessLicenseOutputDto getShopBusinessLicense(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_food_business_license")
    FoodBusinessLicenseOutputDto getFoodBusinessLicense(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_disinfecting_contract")
    OpenApiDisinfectingContractOutputDto getDisinfectingContract(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_shop_front_photo")
    OpenApiShopFrontPhotoOutputDto getShopFrontPhoto(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_by_id")
    DataShopDetailOutputDto getById(@RequestBody @Validated IdRequest idRequest);

    @PostMapping("get_by_code")
    DataShopDetailOutputDto getByCode(@RequestBody @Validated DataShopCodeInoutDto inoutDto);

    @PostMapping("list_areaCode_by_shopIdSet")
    List<String> listAreaCodeByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_shopId_by_shopCodeSet")
    List<String> listShopIdByShopCodeSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_shopId_by_areaCodeSet")
    List<String> listShopIdByAreaCodeSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_shopCodeSet")
    List<DataShopDetailOutputDto> listByShopCodeSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_idSet")
    List<DataShopDetailOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataShopDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<DataShopListOutputDto> listByOffset(@RequestBody @Validated DataShopQueryListOffsetInputDto inputDto);

    @PostMapping("list_all")
    List<DataShopListOutputDto> listAll(@RequestBody @Validated DataShopQueryListAllInputDto inputDto);

    @PostMapping("page")
    PageResponse<DataShopListOutputDto> page(@RequestBody @Validated DataShopQueryPageInputDto inputDto);

    @PostMapping("map_addressInfo")
    Map<String, AddressInfoDto> mapAddressInfo(@RequestBody @Validated IdSetRequest request);
}
