package com.machine.client.data.shop;

import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/shop",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataShopClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataShopCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataShopUpdateInputDto inputDto);

    @PostMapping("update_businessStatus")
    int updateBusinessStatus(@RequestBody @Validated DataShopUpdateShopBusinessStatusInputDto inputDto);

    @PostMapping("update_operationStatus")
    int updateOperationStatus(@RequestBody @Validated DataShopUpdateShopOperationStatusInputDto inputDto);

    @PostMapping("update_physicalStatus")
    int updatePhysicalStatus(@RequestBody @Validated DataShopUpdateShopPhysicalStatusInputDto inputDto);

    @PostMapping("update_shop_business_license")
    int updateShopBusinessLicense(@RequestBody @Validated DataShopUpdateShopBusinessLicenseInputDto inputDto);

    @PostMapping("update_food_business_license")
    int updateFoodBusinessLicense(@RequestBody @Validated DataShopUpdateFoodBusinessLicenseInputDto inputDto);

    @PostMapping("update_disinfecting_contract")
    int updateDisinfectingContract(@RequestBody @Validated DataShopUpdateDisinfectingContractInputDto inputDto);

    @PostMapping("update_shop_front_photo")
    int updateShopFrontPhoto(@RequestBody @Validated DataShopUpdateShopFrontPhotoInputDto inputDto);

    @PostMapping("update_label_option")
    int updateLabelOption(@RequestBody @Validated DataShopUpdateShopLabelOptionInputDto inputDto);

    @PostMapping("batch_update_label_option")
    int batchUpdateLabelOption(@RequestBody @Validated DataShopBatchUpdateShopLabelOptionInputDto inputDto);

    @PostMapping("count_not_bind_Organization")
    int countNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto);

    @PostMapping("get_id_by_code")
    String getIdByCode(@RequestBody @Validated DataShopCodeInoutDto inoutDto);

    @PostMapping("get_shop_business_license")
    DataShopBusinessLicenseOutputDto getShopBusinessLicense(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_food_business_license")
    DataFoodBusinessLicenseOutputDto getFoodBusinessLicense(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_disinfecting_contract")
    DataOpenApiDisinfectingContractOutputDto getDisinfectingContract(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_shop_front_photo")
    DataOpenApiShopFrontPhotoOutputDto getShopFrontPhoto(@RequestBody @Validated IdRequest inoutDto);

    @PostMapping("get_by_id")
    DataShopDetailOutputDto getById(@RequestBody @Validated IdRequest request);

    @PostMapping("list_not_bind_Organization")
    List<String> listNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto);

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

    @PostMapping("id_by_codeSet")
    Map<String, String> idByCodeSet(@RequestBody @Validated OpenApiShopCodeSetInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, DataShopDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<DataShopListOutputDto> listByOffset(@RequestBody @Validated DataShopQueryListOffsetInputDto inputDto);

    @PostMapping("list_all")
    List<DataShopListSimpleOutputDto> listAll(@RequestBody @Validated DataShopQueryListAllInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<DataShopListOutputDto> selectPage(@RequestBody @Validated DataShopQueryPageInputDto inputDto);

    @PostMapping("export_shop")
    String exportShop(@RequestBody @Validated DataShopExportInputDto inputDto);

}
