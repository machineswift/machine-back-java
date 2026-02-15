
package com.machine.service.data.shop.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.sdk.common.envm.base.StorageTypeEnum;
import com.machine.sdk.common.envm.data.shop.DataShopBusinessStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopOperationStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopPhysicalStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import com.machine.sdk.common.model.response.IdCodeResponse;
import com.machine.service.data.shop.dao.mapper.entity.DataShopEntity;

import java.util.List;
import java.util.Set;

public interface IDataShopDao {

    String insert(DataShopEntity entity);

    int update(DataShopEntity entity);

    int updateBusinessStatus(String id,
                             DataShopBusinessStatusEnum businessStatus);

    int updateOperationStatus(String id,
                              DataShopOperationStatusEnum operationStatus);

    int updatePhysicalStatus(String id,
                             DataShopPhysicalStatusEnum physicalStatus);

    int updateShopBusinessLicense(String id,
                                  StorageTypeEnum persistenceStatus,
                                  DataShopBusinessLicenseDto businessLicense);

    int updateFoodBusinessLicense(String id,
                                  StorageTypeEnum persistenceStatus,
                                  DataShopFoodBusinessLicenseDto businessLicense);

    int updateDisinfectingContract(String id,
                                   StorageTypeEnum persistenceStatus,
                                   DataShopDisinfectingContractDto contract);

    int updateShopFrontPhoto(String id,
                             StorageTypeEnum persistenceStatus,
                             DataShopFrontPhotoDto shopFrontPhoto);

    int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    DataShopBusinessLicenseDto shopBusinessLicense(String id,
                                                   StorageTypeEnum persistenceStatus);

    DataShopFoodBusinessLicenseDto foodBusinessLicense(String id,
                                                       StorageTypeEnum persistenceStatus);

    DataShopDisinfectingContractDto disinfectingContract(String id,
                                                         StorageTypeEnum persistenceStatus);

    DataShopFrontPhotoDto shopFrontPhoto(String id,
                                         StorageTypeEnum persistenceStatus);

    String getIdByCode(String code);

    DataShopEntity getById(String id);

    DataShopEntity getByCode(String code);

    DataShopEntity getByName(String name);

    List<String> listAreaCodeByShopIdSet(Set<String> shopIdSet);

    List<String> listShopIdByShopCodeSet(Set<String> shopCodeSet);

    List<String> listShopIdByAreaCodeSet(Set<String> areaCodeSet);

    List<DataShopEntity> selectByIdSet(Set<String> idSet);

    List<DataShopEntity> selectByCodeSet(Set<String> codeSet);

    List<IdCodeResponse> selectSimpleByCodeSet(Set<String> codeSet);

    List<DataShopEntity> listByOffset(DataShopQueryListOffsetInputDto inputDto);

    List<DataShopListSimpleOutputDto> listAll(DataShopQueryListAllInputDto inputDto);

    Page<DataShopEntity> selectPage(DataShopQueryPageInputDto inputDto);

    Page<DataShopEntity> pageCollectShop(DataSuperShopListCollectShopInputDto inputDto);

    List<DataShopEntity> listForExport(DataShopExportInputDto inputDto);

}
