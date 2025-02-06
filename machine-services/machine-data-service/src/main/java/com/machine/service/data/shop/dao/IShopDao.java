
package com.machine.service.data.shop.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopFrontPhotoDto;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;

import java.util.List;
import java.util.Set;

public interface IShopDao {

    String insert(ShopEntity entity);

    int updateBaseInfo(ShopEntity entity);

    int updateStatus(String id,
                     ShopStatusEnum status);

    void updateAddress(String id,
                       AddressInfoDto addressInfo);

    int updateShopBusinessLicense(String id,
                                  DataPersistenceStatusEnum persistenceStatus,
                                  ShopBusinessLicenseDto businessLicense);

    int updateFoodBusinessLicense(String id,
                                  DataPersistenceStatusEnum persistenceStatus,
                                  FoodBusinessLicenseDto businessLicense);

    int updateDisinfectingContract(String id,
                                   DataPersistenceStatusEnum persistenceStatus,
                                   DisinfectingContractDto contract);

    int updateShopFrontPhoto(String id,
                             DataPersistenceStatusEnum persistenceStatus,
                             ShopFrontPhotoDto shopFrontPhoto);

    int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto);

    AddressInfoDto getAddress(String id);

    ShopBusinessLicenseDto shopBusinessLicense(String id,
                                               DataPersistenceStatusEnum persistenceStatus);

    FoodBusinessLicenseDto foodBusinessLicense(String id,
                                               DataPersistenceStatusEnum persistenceStatus);

    DisinfectingContractDto disinfectingContract(String id,
                                                 DataPersistenceStatusEnum persistenceStatus);

    ShopFrontPhotoDto shopFrontPhoto(String id,
                                     DataPersistenceStatusEnum persistenceStatus);

    ShopEntity getById(String id);

    ShopEntity getByCode(String shopCode);

    List<String> listAreaCodeByShopIdSet(Set<String> shopIdSet);

    List<String> listShopIdByShopCodeSet(Set<String> shopCodeSet);

    List<String> listShopIdByAreaCodeSet(Set<String> areaCodeSet);

    List<ShopEntity> selectByIdSet(Set<String> idSet);

    List<ShopEntity> selectByCodeSet(Set<String> codeSet);

    List<ShopEntity> listByOffset(DataShopQueryListOffsetInputDto inputDto);

    List<ShopEntity> listAll(DataShopQueryListAllInputDto inputDto);

    Page<ShopEntity> pageShop(DataShopQueryPageInputDto inputDto);

    Page<ShopEntity> pageCollectShop(SuperShopListCollectShopInputDto inputDto);
}
