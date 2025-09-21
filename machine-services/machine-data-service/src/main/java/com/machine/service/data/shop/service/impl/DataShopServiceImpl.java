package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.IdCodeResponse;
import com.machine.service.data.shop.dao.IDataShopDao;
import com.machine.service.data.shop.dao.IDataShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.DataShopEntity;
import com.machine.service.data.shop.dao.mapper.entity.DataShopLabelOptionRelationEntity;
import com.machine.service.data.shop.service.IDataShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataShopServiceImpl implements IDataShopService {

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Autowired
    private IDataShopDao shopDao;

    @Autowired
    private IDataShopLabelOptionRelationDao shopLabelOptionRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataShopCreateInputDto inputDto) {
        DataShopEntity entityByName = shopDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.shop.service.create.nameAlreadyExists", "门店名称已经存在");
        }

        DataShopEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataShopEntity.class,true);
        insertEntity.setCode(leaf4DataCodeClient.shopCode());
        insertEntity.setBusinessStatus(DataShopBusinessStatusEnum.MARKET_RESEARCH);
        insertEntity.setOperationStatus(DataShopOperationStatusEnum.CLOSED);
        insertEntity.setPhysicalStatus(DataShopPhysicalStatusEnum.IDLE);

        AddressInfoDto addressInfo=inputDto.getAddressInfo();
        if (addressInfo != null) {
            insertEntity.setCountryCode(addressInfo.getCountryCode());
            insertEntity.setProvinceCode(addressInfo.getProvinceCode());
            insertEntity.setCityCode(addressInfo.getCityCode());
            insertEntity.setAreaCode(addressInfo.getAreaCode());
            insertEntity.setAddress(addressInfo.getAddress());
        }
        return shopDao.insert(insertEntity);
    }

    @Override
    public int update(DataShopUpdateInputDto inputDto) {
        DataShopEntity entity = shopDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证名称在同一层级是否存在
        DataShopEntity entityByName = shopDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.shop.service.update.nameAlreadyExists", "门店名称已经存在");
        }

        DataShopEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataShopEntity.class,true);
        AddressInfoDto addressInfo=inputDto.getAddressInfo();
        if (addressInfo != null) {
            updateEntity.setCountryCode(addressInfo.getCountryCode());
            updateEntity.setProvinceCode(addressInfo.getProvinceCode());
            updateEntity.setCityCode(addressInfo.getCityCode());
            updateEntity.setAreaCode(addressInfo.getAreaCode());
            updateEntity.setAddress(addressInfo.getAddress());
        }
        return shopDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBusinessStatus(DataShopUpdateShopBusinessStatusInputDto inputDto) {
        DataShopEntity entity = shopDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getBusinessStatus() == inputDto.getBusinessStatus()) {
            return 0;
        }

        //todo machine 门店状态逻辑校验

        return shopDao.updateBusinessStatus(inputDto.getId(),inputDto.getBusinessStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOperationStatus(DataShopUpdateShopOperationStatusInputDto inputDto) {
        DataShopEntity entity = shopDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getOperationStatus() == inputDto.getOperationStatus()) {
            return 0;
        }

        //todo machine 门店状态逻辑校验

        return shopDao.updateOperationStatus(inputDto.getId(),inputDto.getOperationStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhysicalStatus(DataShopUpdateShopPhysicalStatusInputDto inputDto) {
        DataShopEntity entity = shopDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getPhysicalStatus() == inputDto.getPhysicalStatus()) {
            return 0;
        }

        //todo machine 门店状态逻辑校验

        return shopDao.updatePhysicalStatus(inputDto.getId(),inputDto.getPhysicalStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLabel(DataShopUpdateShopLabelOptionInputDto inputDto) {
        DataShopEntity entity = shopDao.getById(inputDto.getId());
        if (entity == null) {
            return 0;
        }

        shopLabelOptionRelationDao.deleteByShopId(inputDto.getId());
        shopLabelOptionRelationDao.batchInsert(inputDto.getId(), inputDto.getLabelOptionIdSet());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdateLabel(DataShopBatchUpdateShopLabelOptionInputDto inputDto) {
        if (CollectionUtil.isEmpty(inputDto.getShopIdSet())) {
            return 0;
        }

        //查询已经存在对应标签的门店
        Set<String> dbShopIdSet = new HashSet<>();
        List<DataShopLabelOptionRelationEntity> entityList = shopLabelOptionRelationDao.listByLabelOptionId(inputDto.getLabelOptionIdId());
        if (CollectionUtil.isNotEmpty(entityList)) {
            dbShopIdSet.addAll(entityList.stream().map(DataShopLabelOptionRelationEntity::getShopId).collect(Collectors.toSet()));
        }

        //移除已经打上标签的门店
        Set<String> inputShopIdSet = inputDto.getShopIdSet();
        inputShopIdSet.removeAll(dbShopIdSet);

        return shopLabelOptionRelationDao.batchUpdateLabel(inputDto.getLabelOptionIdId(), inputShopIdSet);
    }

    @Override
    public int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopDao.countNotBindOrganization(inputDto);
    }

    @Override
    public String getIdByCode(DataShopCodeInoutDto inoutDto) {
        return shopDao.getIdByCode(inoutDto.getCode());
    }

    @Override
    public List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopDao.listNotBindOrganization(inputDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopBusinessLicense(DataShopUpdateShopBusinessLicenseInputDto inputDto) {
        DataShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateShopBusinessLicense(dbEntity.getId(), inputDto.getPersistenceStatus(),
                inputDto.getBusinessLicense());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateFoodBusinessLicense(DataShopUpdateFoodBusinessLicenseInputDto inputDto) {
        DataShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateFoodBusinessLicense(dbEntity.getId(), inputDto.getPersistenceStatus(),
                inputDto.getFoodBusinessLicense());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDisinfectingContract(DataShopUpdateDisinfectingContractInputDto inputDto) {
        DataShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateDisinfectingContract(dbEntity.getId(), inputDto.getPersistenceStatus(),
                inputDto.getDisinfectingContractDto());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopFrontPhoto(DataShopUpdateShopFrontPhotoInputDto inputDto) {
        DataShopEntity dbEntity = shopDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }
        return shopDao.updateShopFrontPhoto(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getShopFrontPhoto());
    }

    @Override
    public DataShopBusinessLicenseOutputDto getShopBusinessLicense(IdRequest request) {
        DataShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataShopBusinessLicenseOutputDto outputDto = new DataShopBusinessLicenseOutputDto();
        outputDto.setTemporaryBusinessLicense(shopDao.shopBusinessLicense(dbEntity.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentBusinessLicense(shopDao.shopBusinessLicense(dbEntity.getId(), SystemStorageTypeEnum.PERMANENT));
        { //证件状态
            DataShopBusinessLicenseDto permanent = outputDto.getPermanentBusinessLicense();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public DataFoodBusinessLicenseOutputDto getFoodBusinessLicense(IdRequest request) {
        DataShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataFoodBusinessLicenseOutputDto outputDto = new DataFoodBusinessLicenseOutputDto();
        outputDto.setTemporaryFoodBusinessLicense(shopDao.foodBusinessLicense(dbEntity.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentFoodBusinessLicense(shopDao.foodBusinessLicense(dbEntity.getId(), SystemStorageTypeEnum.PERMANENT));
        { //证件状态
            DataShopFoodBusinessLicenseDto permanent = outputDto.getPermanentFoodBusinessLicense();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public DataOpenApiDisinfectingContractOutputDto getDisinfectingContract(IdRequest request) {
        DataShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataOpenApiDisinfectingContractOutputDto outputDto = new DataOpenApiDisinfectingContractOutputDto();
        outputDto.setTemporaryDisinfectingContract(shopDao.disinfectingContract(dbEntity.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentDisinfectingContract(shopDao.disinfectingContract(dbEntity.getId(), SystemStorageTypeEnum.PERMANENT));

        { //证件状态
            DataShopDisinfectingContractDto permanent = outputDto.getPermanentDisinfectingContract();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                Long expiryDate = permanent.getExpiryDate();
                if (expiryDate.compareTo(System.currentTimeMillis()) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.EXPIRED);
                } else if (expiryDate.compareTo(System.currentTimeMillis() +
                        permanent.getImpendingReminderRule() * 24 * 3600 * 1000) < 0) {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NEAR_EXPIRATION);
                } else {
                    outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
                }
            }
        }
        return outputDto;
    }

    @Override
    public DataOpenApiShopFrontPhotoOutputDto getShopFrontPhoto(IdRequest request) {
        DataShopEntity dbEntity = shopDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataOpenApiShopFrontPhotoOutputDto outputDto = new DataOpenApiShopFrontPhotoOutputDto();
        outputDto.setTemporaryShopFrontPhoto(shopDao.shopFrontPhoto(dbEntity.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentShopFrontPhoto(shopDao.shopFrontPhoto(dbEntity.getId(), SystemStorageTypeEnum.PERMANENT));

        { //证件状态
            DataShopFrontPhotoDto permanent = outputDto.getPermanentShopFrontPhoto();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public DataShopDetailOutputDto getById(IdRequest request) {
        DataShopEntity entity = shopDao.getById(request.getId());
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
        List<DataShopEntity> userEntityList = shopDao.selectByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopDetailOutputDto.class);
    }

    @Override
    public List<DataShopDetailOutputDto> listByShopCodeSet(IdSetRequest request) {
        List<DataShopEntity> userEntityList = shopDao.selectByCodeSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopDetailOutputDto.class);
    }

    @Override
    public Map<String, String> idByCodeSet(OpenApiShopCodeSetInputDto inputDto) {
        List<IdCodeResponse> responseList = shopDao.selectSimpleByCodeSet(inputDto.getCodeSet());
        if (CollectionUtil.isEmpty(responseList)) {
            return Map.of();
        }

        return responseList.stream()
                .collect(Collectors.toMap(IdCodeResponse::getCode, IdCodeResponse::getId));
    }

    @Override
    public Map<String, DataShopDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<DataShopEntity> userEntityList = shopDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(DataShopEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataShopDetailOutputDto.class)));
    }

    @Override
    public List<DataShopListOutputDto> listByOffset(DataShopQueryListOffsetInputDto inputDto) {
        List<DataShopEntity> userEntityList = shopDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(userEntityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(userEntityList), DataShopListOutputDto.class);
    }

    @Override
    public List<DataShopListSimpleOutputDto> listAll(DataShopQueryListAllInputDto inputDto) {
        return shopDao.listAll(inputDto);
    }

    @Override
    public Page<DataShopListOutputDto> selectPage(DataShopQueryPageInputDto inputDto) {
        Page<DataShopEntity> page = shopDao.selectPage(inputDto);
        Page<DataShopListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataShopListOutputDto.class));
        return pageResult;
    }
}
