package com.machine.service.data.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataShopService {

    String create(DataShopCreateInputDto inputDto);

    int update(DataShopUpdateInputDto inputDto);

    int updateBusinessStatus(DataShopUpdateShopBusinessStatusInputDto inputDto);

    int updateOperationStatus(DataShopUpdateShopOperationStatusInputDto inputDto);

    int updatePhysicalStatus(DataShopUpdateShopPhysicalStatusInputDto inputDto);

    int updateLabel(DataShopUpdateShopLabelOptionInputDto inputDto);

    int batchUpdateLabel(DataShopBatchUpdateShopLabelOptionInputDto inputDto);

    int updateShopBusinessLicense(DataShopUpdateShopBusinessLicenseInputDto inputDto);

    int updateFoodBusinessLicense(DataShopUpdateFoodBusinessLicenseInputDto inputDto);

    int updateDisinfectingContract(DataShopUpdateDisinfectingContractInputDto inputDto);

    int updateShopFrontPhoto(DataShopUpdateShopFrontPhotoInputDto inputDto);

    int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    String getIdByCode(DataShopCodeInoutDto inoutDto);

    DataShopDetailOutputDto getById(IdRequest request);

    List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    DataShopBusinessLicenseOutputDto getShopBusinessLicense(IdRequest request);

    DataFoodBusinessLicenseOutputDto getFoodBusinessLicense(IdRequest request);

    DataOpenApiDisinfectingContractOutputDto getDisinfectingContract(IdRequest request);

    DataOpenApiShopFrontPhotoOutputDto getShopFrontPhoto(IdRequest request);

    List<String> listAreaCodeByShopIdSet(IdSetRequest request);

    List<String> listShopIdByShopCodeSet(IdSetRequest request);

    List<String> listShopIdByAreaCodeSet(IdSetRequest request);

    List<DataShopDetailOutputDto> listByIdSet(IdSetRequest request);

    List<DataShopDetailOutputDto> listByShopCodeSet(IdSetRequest request);

    Map<String, String> idByCodeSet(OpenApiShopCodeSetInputDto inputDto);

    Map<String, DataShopDetailOutputDto> mapByIdSet(IdSetRequest request);

    List<DataShopListOutputDto> listByOffset(DataShopQueryListOffsetInputDto inputDto);

    List<DataShopListSimpleOutputDto> listAll(DataShopQueryListAllInputDto inputDto);

    Page<DataShopListOutputDto> selectPage(DataShopQueryPageInputDto inputDto);

}
