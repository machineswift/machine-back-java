package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.envm.data.CertificateStatusEnum;
import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopFrontPhotoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.dao.IShopDao;
import com.machine.service.data.shop.dao.IShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;
import com.machine.service.data.shop.dao.mapper.entity.ShopLabelOptionRelationEntity;
import com.machine.service.data.shop.service.IShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShopServiceImpl implements IShopService {

    @Autowired
    private IShopDao shopDao;

    @Autowired
    private IShopLabelOptionRelationDao shopLabelOptionRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataShopCreateInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getByCode(inputDto.getCode());
        if (null != dbEntity) {
            throw new IamBusinessException("iam.shop.create.codeAlreadyExists", "编码已经存在");
        }

        ShopEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ShopEntity.class);
        return shopDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLabel(ShopUpdateShopLabelOptionInputDto inputDto) {
        ShopEntity entity = shopDao.getById(inputDto.getId());
        if (entity == null) {
            return 0;
        }

        shopLabelOptionRelationDao.deleteByShopId(inputDto.getId());
        shopLabelOptionRelationDao.batchInsert(inputDto.getId(), inputDto.getLabelOptionIdSet());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateLabel(ShopBatchUpdateShopLabelOptionInputDto inputDto) {
        if (CollectionUtil.isEmpty(inputDto.getShopIdSet())) {
            return 0;
        }

        //查询已经存在对应标签的门店
        Set<String> dbShopIdSet = new HashSet<>();
        List<ShopLabelOptionRelationEntity> entityList = shopLabelOptionRelationDao.listByLabelOptionId(inputDto.getLabelOptionIdId());
        if (CollectionUtil.isNotEmpty(entityList)) {
            dbShopIdSet.addAll(entityList.stream().map(ShopLabelOptionRelationEntity::getShopId).collect(Collectors.toSet()));
        }

        //移除已经打上标签的门店
        Set<String> inputShopIdSet = inputDto.getShopIdSet();
        inputShopIdSet.removeAll(dbShopIdSet);

        return shopLabelOptionRelationDao.batchUpdateLabel(inputDto.getLabelOptionIdId(), inputShopIdSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBaseInfo(DataShopUpdateShopBaseInfoInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        ShopEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ShopEntity.class);
        updateEntity.setCode(null);
        updateEntity.setId(dbEntity.getId());
        return shopDao.updateBaseInfo(updateEntity);
    }

    @Override
    public int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopDao.countNotBindOrganization(inputDto);
    }

    @Override
    public List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopDao.listNotBindOrganization(inputDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(ShopUpdateShopStatusInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (ShopStatusEnum.PERMANENTLY_CLOSED.equals(dbEntity.getStatus())) {
            log.info(inputDto.getId() + "门店当前是永久闭店状态，不能修改成其他状态");
            return 0;
        }

        return shopDao.updateStatus(dbEntity.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOpeningDate(ShopUpdateOpeningDateInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getByCode(inputDto.getCode());
        if (dbEntity == null) {
            return 0;
        }
        if (dbEntity.getOpeningDate().equals(inputDto.getOpeningDate())) {
            return 0;
        }
        dbEntity.setOpeningDate(inputDto.getOpeningDate());
        return shopDao.updateBaseInfo(dbEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAddress(OpenApiShopUpdateAddressInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        // 地址信息
        AddressInfoDto addressInfo = inputDto.getAddressInfo();

        ShopEntity updateEntity = new ShopEntity();
        updateEntity.setId(dbEntity.getId());
        updateEntity.setCountryCode(addressInfo.getCountryCode());
        updateEntity.setProvinceCode(addressInfo.getProvinceCode());
        updateEntity.setCityCode(addressInfo.getCityCode());
        updateEntity.setAreaCode(addressInfo.getAreaCode());
        shopDao.updateBaseInfo(updateEntity);
        shopDao.updateAddress(dbEntity.getId(), inputDto.getAddressInfo());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopBusinessLicense(OpenApiShopUpdateShopBusinessLicenseInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateShopBusinessLicense(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getBusinessLicense());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFoodBusinessLicense(OpenApiShopUpdateFoodBusinessLicenseInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateFoodBusinessLicense(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getBusinessLicense());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDisinfectingContract(OpenApiShopUpdateDisinfectingContractInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateDisinfectingContract(dbEntity.getId(), inputDto.getPersistenceStatus(),
                inputDto.getDisinfectingContractDto());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopFrontPhoto(OpenApiShopUpdateShopFrontPhotoInputDto inputDto) {
        ShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateShopFrontPhoto(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getShopFrontPhoto());
    }

    @Override
    public AddressInfoDto getAddressInfo(IdRequest request) {
        return shopDao.getAddress(request.getId());
    }

    @Override
    public ShopBusinessLicenseOutputDto getShopBusinessLicense(IdRequest request) {
        ShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        ShopBusinessLicenseOutputDto outputDto = new ShopBusinessLicenseOutputDto();
        outputDto.setTemporaryBusinessLicense(shopDao.shopBusinessLicense(dbEntity.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentBusinessLicense(shopDao.shopBusinessLicense(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));
        { //证件状态
            ShopBusinessLicenseDto permanent = outputDto.getPermanentBusinessLicense();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public FoodBusinessLicenseOutputDto getFoodBusinessLicense(IdRequest request) {
        ShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        FoodBusinessLicenseOutputDto outputDto = new FoodBusinessLicenseOutputDto();
        outputDto.setTemporaryBusinessLicense(shopDao.foodBusinessLicense(dbEntity.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentBusinessLicense(shopDao.foodBusinessLicense(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));
        { //证件状态
            FoodBusinessLicenseDto permanent = outputDto.getPermanentBusinessLicense();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public OpenApiDisinfectingContractOutputDto getDisinfectingContract(IdRequest request) {
        ShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        OpenApiDisinfectingContractOutputDto outputDto = new OpenApiDisinfectingContractOutputDto();
        outputDto.setTemporaryContract(shopDao.disinfectingContract(dbEntity.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentContract(shopDao.disinfectingContract(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));

        { //证件状态
            DisinfectingContractDto permanent = outputDto.getPermanentContract();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public OpenApiShopFrontPhotoOutputDto getShopFrontPhoto(IdRequest request) {
        ShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        OpenApiShopFrontPhotoOutputDto outputDto = new OpenApiShopFrontPhotoOutputDto();
        outputDto.setTemporaryShopFrontPhoto(shopDao.shopFrontPhoto(dbEntity.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentShopFrontPhoto(shopDao.shopFrontPhoto(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));

        { //证件状态
            ShopFrontPhotoDto permanent = outputDto.getPermanentShopFrontPhoto();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public DataShopDetailOutputDto getById(IdRequest request) {
        ShopEntity entity = shopDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataShopDetailOutputDto.class);
    }

    @Override
    public DataShopDetailOutputDto getByCode(DataShopCodeInoutDto inoutDto) {
        ShopEntity entity = shopDao.getByCode(inoutDto.getCode());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataShopDetailOutputDto.class);
    }

    @Override
    public List<String> listAreaCodeByShopIdSet(IdSetRequest request) {
        return shopDao.listAreaCodeByShopIdSet(request.getIdSet());
    }

    @Override
    public List<String> listShopIdByShopCodeSet(IdSetRequest request) {
        return shopDao.listShopIdByShopCodeSet(request.getIdSet());
    }

    @Override
    public List<String> listShopIdByAreaCodeSet(IdSetRequest request) {
        return shopDao.listShopIdByAreaCodeSet(request.getIdSet());
    }

    @Override
    public List<DataShopDetailOutputDto> listByIdSet(IdSetRequest request) {
        List<ShopEntity> userEntityList = shopDao.selectByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopDetailOutputDto.class);
    }

    @Override
    public List<DataShopDetailOutputDto> listByShopCodeSet(IdSetRequest request) {
        List<ShopEntity> userEntityList = shopDao.selectByCodeSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopDetailOutputDto.class);
    }

    @Override
    public Map<String, DataShopDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<ShopEntity> userEntityList = shopDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(ShopEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataShopDetailOutputDto.class)));
    }

    @Override
    public List<DataShopListOutputDto> listByOffset(DataShopQueryListOffsetInputDto inputDto) {
        List<ShopEntity> userEntityList = shopDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopListOutputDto.class);
    }

    @Override
    public List<DataShopListOutputDto> listAll(DataShopQueryListAllInputDto inputDto) {
        List<ShopEntity> userEntityList = shopDao.listAll(inputDto);
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopListOutputDto.class);
    }

    @Override
    public Page<DataShopListOutputDto> pageShop(DataShopQueryPageInputDto inputDto) {
        Page<ShopEntity> page = shopDao.pageShop(inputDto);
        Page<DataShopListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataShopListOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, AddressInfoDto> mapAddressInfo(IdSetRequest request) {
        Map<String, AddressInfoDto> addressInfoMap = new HashMap<>();
        for (String shopId : request.getIdSet()) {
            AddressInfoDto addressInfoDto = shopDao.getAddress(shopId);
            if (addressInfoDto != null) {
                addressInfoMap.put(shopId, addressInfoDto);
            }
        }
        return addressInfoMap;
    }
}
