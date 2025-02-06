package com.machine.service.data.shop.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.shop.service.IShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/shop")
public class DataShopServer implements IDataShopClient {

    @Autowired
    private IShopService shopService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataShopCreateInputDto inputDto) {
        log.info("创建门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.create(inputDto);
    }

    @Override
    @PostMapping("update_label_option")
    public int updateLabelOption(@RequestBody @Validated ShopUpdateShopLabelOptionInputDto inputDto) {
        log.info("修改门店标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateLabel(inputDto);
    }

    @Override
    @PostMapping("batch_update_label_option")
    public int batchUpdateLabelOption(@RequestBody @Validated ShopBatchUpdateShopLabelOptionInputDto inputDto) {
        log.info("批量修改门店标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.batchUpdateLabel(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated ShopUpdateShopStatusInputDto inputDto) {
        log.info("修改门店状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_opening_date")
    public int updateOpeningDate(@RequestBody @Validated ShopUpdateOpeningDateInputDto inputDto) {
        log.info("修改门店开业时间，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateOpeningDate(inputDto);
    }

    @Override
    @PostMapping("update_address")
    public int updateAddress(@RequestBody @Validated OpenApiShopUpdateAddressInputDto inputDto) {
        log.info("修改门店地址信息，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateAddress(inputDto);
    }

    @Override
    @PostMapping("update_shop_business_license")
    public int updateShopBusinessLicense(OpenApiShopUpdateShopBusinessLicenseInputDto inputDto) {
        log.info("修改门店营业执照，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateShopBusinessLicense(inputDto);
    }

    @Override
    @PostMapping("update_food_business_license")
    public int updateFoodBusinessLicense(OpenApiShopUpdateFoodBusinessLicenseInputDto inputDto) {
        log.info("修改食品经营许可证，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateFoodBusinessLicense(inputDto);
    }

    @Override
    @PostMapping("update_disinfecting_contract")
    public int updateDisinfectingContract(@RequestBody @Validated OpenApiShopUpdateDisinfectingContractInputDto inputDto) {
        log.info("修改消杀合同，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateDisinfectingContract(inputDto);
    }

    @Override
    public int updateShopFrontPhoto(OpenApiShopUpdateShopFrontPhotoInputDto inputDto) {
        log.info("修改门头照，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateShopFrontPhoto(inputDto);
    }

    @Override
    @PostMapping("update_base_info")
    public int updateBaseInfo(@RequestBody @Validated DataShopUpdateShopBaseInfoInputDto inputDto) {
        log.info("修改门店基础信息，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateBaseInfo(inputDto);
    }

    @Override
    @PostMapping("count_not_bind_Organization")
    public int countNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto) {
        return shopService.countNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("list_not_bind_Organization")
    public List<String> listNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto) {
        return shopService.listNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("get_addressInfo")
    public AddressInfoDto getAddressInfo(@RequestBody @Validated IdRequest request) {
        return shopService.getAddressInfo(request);
    }

    @Override
    @PostMapping("get_shop_business_license")
    public ShopBusinessLicenseOutputDto getShopBusinessLicense(@RequestBody @Validated IdRequest request) {
        return shopService.getShopBusinessLicense(request);
    }

    @Override
    @PostMapping("get_food_business_license")
    public FoodBusinessLicenseOutputDto getFoodBusinessLicense(@RequestBody @Validated IdRequest request) {
        return shopService.getFoodBusinessLicense(request);
    }

    @Override
    @PostMapping("get_disinfecting_contract")
    public OpenApiDisinfectingContractOutputDto getDisinfectingContract(@RequestBody @Validated IdRequest request) {
        return shopService.getDisinfectingContract(request);
    }

    @Override
    @PostMapping("get_shop_front_photo")
    public OpenApiShopFrontPhotoOutputDto getShopFrontPhoto(@RequestBody @Validated IdRequest inoutDto) {
        return shopService.getShopFrontPhoto(inoutDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataShopDetailOutputDto getById(@RequestBody @Validated IdRequest idRequest) {
        return shopService.getById(idRequest);
    }

    @Override
    @PostMapping("get_by_code")
    public DataShopDetailOutputDto getByCode(@RequestBody @Validated DataShopCodeInoutDto inoutDto) {
        return shopService.getByCode(inoutDto);
    }

    @Override
    @PostMapping("list_areaCode_by_shopIdSet")
    public List<String> listAreaCodeByShopIdSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.listAreaCodeByShopIdSet(request);
    }

    @Override
    @PostMapping("list_shopId_by_shopCodeSet")
    public List<String> listShopIdByShopCodeSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.listShopIdByShopCodeSet(request);
    }

    @Override
    @PostMapping("list_shopId_by_areaCodeSet")
    public List<String> listShopIdByAreaCodeSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.listShopIdByAreaCodeSet(request);
    }

    @Override
    @PostMapping("list_by_shopCodeSet")
    public List<DataShopDetailOutputDto> listByShopCodeSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.listByShopCodeSet(request);
    }

    @Override
    @PostMapping("list_by_idSet")
    public List<DataShopDetailOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.listByIdSet(request);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataShopDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return shopService.mapByIdSet(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataShopListOutputDto> listByOffset(@RequestBody @Validated DataShopQueryListOffsetInputDto inputDto) {
        return shopService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("list_all")
    public List<DataShopListOutputDto> listAll(@RequestBody @Validated DataShopQueryListAllInputDto inputDto) {
        return shopService.listAll(inputDto);
    }

    @Override
    @PostMapping("page")
    public PageResponse<DataShopListOutputDto> page(@RequestBody @Validated DataShopQueryPageInputDto inputDto) {
        Page<DataShopListOutputDto> pageResult = shopService.pageShop(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("map_addressInfo")
    public Map<String, AddressInfoDto> mapAddressInfo(@RequestBody @Validated IdSetRequest request) {
        return shopService.mapAddressInfo(request);
    }
}
