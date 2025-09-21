package com.machine.service.data.shop.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.shop.service.IDataShopService;
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
    private IDataShopService shopService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataShopCreateInputDto inputDto) {
        log.info("创建门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataShopUpdateInputDto inputDto) {
        log.info("修改门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.update(inputDto);
    }

    @Override
    @PostMapping("update_businessStatus")
    public int updateBusinessStatus(@RequestBody @Validated DataShopUpdateShopBusinessStatusInputDto inputDto) {
        log.info("修改门店经营状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateBusinessStatus(inputDto);
    }

    @Override
    @PostMapping("update_operationStatus")
    public int updateOperationStatus(@RequestBody @Validated DataShopUpdateShopOperationStatusInputDto inputDto) {
        log.info("修改门店运营状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateOperationStatus(inputDto);
    }

    @Override
    @PostMapping("update_physicalStatus")
    public int updatePhysicalStatus(@RequestBody @Validated DataShopUpdateShopPhysicalStatusInputDto inputDto) {
        log.info("修改门店物理状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updatePhysicalStatus(inputDto);
    }

    @Override
    @PostMapping("update_label_option")
    public int updateLabelOption(@RequestBody @Validated DataShopUpdateShopLabelOptionInputDto inputDto) {
        log.info("修改门店标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateLabel(inputDto);
    }

    @Override
    @PostMapping("batch_update_label_option")
    public int batchUpdateLabelOption(@RequestBody @Validated DataShopBatchUpdateShopLabelOptionInputDto inputDto) {
        log.info("批量修改门店标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.batchUpdateLabel(inputDto);
    }

    @Override
    @PostMapping("update_shop_business_license")
    public int updateShopBusinessLicense(DataShopUpdateShopBusinessLicenseInputDto inputDto) {
        log.info("修改门店营业执照，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateShopBusinessLicense(inputDto);
    }

    @Override
    @PostMapping("update_food_business_license")
    public int updateFoodBusinessLicense(DataShopUpdateFoodBusinessLicenseInputDto inputDto) {
        log.info("修改食品经营许可证，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateFoodBusinessLicense(inputDto);
    }

    @Override
    @PostMapping("update_disinfecting_contract")
    public int updateDisinfectingContract(@RequestBody @Validated DataShopUpdateDisinfectingContractInputDto inputDto) {
        log.info("修改消杀合同，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateDisinfectingContract(inputDto);
    }

    @Override
    public int updateShopFrontPhoto(DataShopUpdateShopFrontPhotoInputDto inputDto) {
        log.info("修改门头照，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return shopService.updateShopFrontPhoto(inputDto);
    }

    @Override
    @PostMapping("count_not_bind_Organization")
    public int countNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto) {
        return shopService.countNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("get_id_by_code")
    public String getIdByCode(@RequestBody @Validated DataShopCodeInoutDto inoutDto) {
        return shopService.getIdByCode(inoutDto);
    }

    @Override
    @PostMapping("list_not_bind_Organization")
    public List<String> listNotBindOrganization(@RequestBody @Validated DataShopNotBindOrganizationInputDto inputDto) {
        return shopService.listNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("get_shop_business_license")
    public DataShopBusinessLicenseOutputDto getShopBusinessLicense(@RequestBody @Validated IdRequest request) {
        return shopService.getShopBusinessLicense(request);
    }

    @Override
    @PostMapping("get_food_business_license")
    public DataFoodBusinessLicenseOutputDto getFoodBusinessLicense(@RequestBody @Validated IdRequest request) {
        return shopService.getFoodBusinessLicense(request);
    }

    @Override
    @PostMapping("get_disinfecting_contract")
    public DataOpenApiDisinfectingContractOutputDto getDisinfectingContract(@RequestBody @Validated IdRequest request) {
        return shopService.getDisinfectingContract(request);
    }

    @Override
    @PostMapping("get_shop_front_photo")
    public DataOpenApiShopFrontPhotoOutputDto getShopFrontPhoto(@RequestBody @Validated IdRequest inoutDto) {
        return shopService.getShopFrontPhoto(inoutDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataShopDetailOutputDto getById(@RequestBody @Validated IdRequest idRequest) {
        return shopService.getById(idRequest);
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
    @PostMapping("id_by_codeSet")
    public Map<String, String> idByCodeSet(@RequestBody @Validated  OpenApiShopCodeSetInputDto inputDto) {
        return shopService.idByCodeSet(inputDto);
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
    public List<DataShopListSimpleOutputDto> listAll(@RequestBody @Validated DataShopQueryListAllInputDto inputDto) {
        return shopService.listAll(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<DataShopListOutputDto> selectPage(@RequestBody @Validated DataShopQueryPageInputDto inputDto) {
        Page<DataShopListOutputDto> pageResult = shopService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

}
