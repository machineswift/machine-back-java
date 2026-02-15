package com.machine.service.data.shop.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.sdk.common.envm.base.StorageTypeEnum;
import com.machine.sdk.common.envm.data.shop.DataShopBusinessStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopOperationStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopPhysicalStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import com.machine.sdk.common.model.response.IdCodeResponse;
import com.machine.sdk.self.domain.data.shop.DataShopDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.external.dao.IDataExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;
import com.machine.service.data.shop.dao.IDataShopDao;
import com.machine.service.data.shop.dao.mapper.DataShopMapper;
import com.machine.service.data.shop.dao.mapper.entity.DataShopEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataShopDaoImpl implements IDataShopDao {

    private static final String TABLE_NAME = "t_shop";

    /**
     * 营业执照信息
     */
    private static final String SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN = "shop_business_license_temporary";
    private static final String SHOP_BUSINESS_LICENSE_PERMANENT_COLUMN = "shop_business_license_permanent";

    /**
     * 食品经营许可证
     */
    private static final String FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN = "food_business_license_temporary";
    private static final String FOOD_BUSINESS_LICENSE_PERMANENT_COLUMN = "food_business_license_permanent";

    /**
     * 消杀合同
     */
    private static final String DISINFECTING_CONTRACT_TEMPORARY_COLUMN = "disinfecting_contract_temporary";
    private static final String DISINFECTING_CONTRACT_PERMANENT_COLUMN = "disinfecting_contract_permanent";

    /**
     * 门头照
     */
    private static final String SHOP_FRONT_PHOTO_TEMPORARY_COLUMN = "shop_front_photo_temporary";
    private static final String SHOP_FRONT_PHOTO_PERMANENT_COLUMN = "shop_front_photo_permanent";


    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataShopMapper shopMapper;

    @Autowired
    private IDataExternalFieldDataDao externalFieldDataDao;

    @Override
    public String insert(DataShopEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_CREATE, new IdDto(entity.getId()));

        shopMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataShopEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE, new IdDto(entity.getId()));

        return shopMapper.updateById(entity);
    }

    @Override
    public int updateBusinessStatus(String id, DataShopBusinessStatusEnum businessStatus) {
        DataShopEntity entity = new DataShopEntity();
        entity.setId(id);
        entity.setBusinessStatus(businessStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_BUSINESS_STATUS,
                new DataShopDto(entity.getId(), businessStatus));

        return shopMapper.updateById(entity);
    }

    @Override
    public int updateOperationStatus(String id, DataShopOperationStatusEnum operationStatus) {
        DataShopEntity entity = new DataShopEntity();
        entity.setId(id);
        entity.setOperationStatus(operationStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_OPERATION_STATUS,
                new DataShopDto(entity.getId(), operationStatus));

        return shopMapper.updateById(entity);
    }

    @Override
    public int updatePhysicalStatus(String id, DataShopPhysicalStatusEnum physicalStatus) {
        DataShopEntity entity = new DataShopEntity();
        entity.setId(id);
        entity.setPhysicalStatus(physicalStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_PHYSICAL_STATUS,
                new DataShopDto(entity.getId(), physicalStatus));

        return shopMapper.updateById(entity);
    }

    @Override
    public int updateShopBusinessLicense(String id,
                                         StorageTypeEnum persistenceStatus,
                                         DataShopBusinessLicenseDto businessLicense) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == businessLicense) {
                return 0;
            }
            fieldName = SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (StorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(id);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(businessLicense));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_SHOP_BUSINESS_LICENSE, new IdDto(id));
        return 1;
    }

    @Override
    public int updateFoodBusinessLicense(String id,
                                         StorageTypeEnum persistenceStatus,
                                         DataShopFoodBusinessLicenseDto businessLicense) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == businessLicense) {
                return 0;
            }
            fieldName = FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = FOOD_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (StorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(id);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(businessLicense));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_FOOD_BUSINESS_LICENSE, new IdDto(id));
        return 1;
    }

    @Override
    public int updateDisinfectingContract(String id,
                                          StorageTypeEnum persistenceStatus,
                                          DataShopDisinfectingContractDto contract) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == contract) {
                return 0;
            }
            fieldName = DISINFECTING_CONTRACT_TEMPORARY_COLUMN;
        } else {
            fieldName = DISINFECTING_CONTRACT_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (StorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(DISINFECTING_CONTRACT_PERMANENT_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(id);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(contract));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_DISINFECTING_CONTRACT, new IdDto(id));
        return 1;
    }

    @Override
    public int updateShopFrontPhoto(String id,
                                    StorageTypeEnum persistenceStatus,
                                    DataShopFrontPhotoDto shopFrontPhoto) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == shopFrontPhoto) {
                return 0;
            }
            fieldName = SHOP_FRONT_PHOTO_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_FRONT_PHOTO_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (StorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(SHOP_FRONT_PHOTO_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(id);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(shopFrontPhoto));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_SHOP_FRONT_PHOTO, new IdDto(id));
        return 1;
    }

    @Override
    public int countNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopMapper.countNotBindOrganization(inputDto);
    }

    @Override
    public List<String> listNotBindOrganization(DataShopNotBindOrganizationInputDto inputDto) {
        return shopMapper.listNotBindOrganization(inputDto);
    }

    @Override
    public DataShopBusinessLicenseDto shopBusinessLicense(String id,
                                                          StorageTypeEnum persistenceStatus) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), DataShopBusinessLicenseDto.class);
    }

    @Override
    public DataShopFoodBusinessLicenseDto foodBusinessLicense(String id, StorageTypeEnum persistenceStatus) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = FOOD_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), DataShopFoodBusinessLicenseDto.class);
    }

    @Override
    public DataShopDisinfectingContractDto disinfectingContract(String id,
                                                                StorageTypeEnum persistenceStatus) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = DISINFECTING_CONTRACT_TEMPORARY_COLUMN;
        } else {
            fieldName = DISINFECTING_CONTRACT_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), DataShopDisinfectingContractDto.class);
    }

    @Override
    public DataShopFrontPhotoDto shopFrontPhoto(String id,
                                                StorageTypeEnum persistenceStatus) {
        String fieldName;
        if (StorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = SHOP_FRONT_PHOTO_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_FRONT_PHOTO_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), DataShopFrontPhotoDto.class);
    }

    @Override
    public String getIdByCode(String code) {
        return shopMapper.getIdByCode(code);
    }

    @Override
    public DataShopEntity getById(String id) {
        return shopMapper.selectById(id);
    }

    @Override
    public DataShopEntity getByCode(String code) {
        Wrapper<DataShopEntity> queryWrapper = new LambdaQueryWrapper<DataShopEntity>()
                .eq(DataShopEntity::getCode, code);
        return shopMapper.selectOne(queryWrapper);
    }

    @Override
    public DataShopEntity getByName(String name) {
        Wrapper<DataShopEntity> queryWrapper = new LambdaQueryWrapper<DataShopEntity>()
                .eq(DataShopEntity::getName, name);
        return shopMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listAreaCodeByShopIdSet(Set<String> shopIdSet) {
        return shopMapper.listAreaCodeByShopIdSet(shopIdSet);
    }

    @Override
    public List<String> listShopIdByShopCodeSet(Set<String> shopCodeSet) {
        return shopMapper.listShopIdByShopCodeSet(shopCodeSet);
    }

    @Override
    public List<String> listShopIdByAreaCodeSet(Set<String> areaCodeSet) {
        if(CollectionUtil.isEmpty(areaCodeSet)){
            return List.of();
        }
        return shopMapper.listShopIdByAreaCodeSet(areaCodeSet);
    }

    @Override
    public List<DataShopEntity> selectByIdSet(Set<String> idSet) {
        Wrapper<DataShopEntity> queryWrapper = new LambdaQueryWrapper<DataShopEntity>()
                .in(DataShopEntity::getId, idSet);
        return shopMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataShopEntity> selectByCodeSet(Set<String> codeSet) {
        Wrapper<DataShopEntity> queryWrapper = new LambdaQueryWrapper<DataShopEntity>()
                .in(DataShopEntity::getCode, codeSet);
        return shopMapper.selectList(queryWrapper);
    }

    @Override
    public List<IdCodeResponse> selectSimpleByCodeSet(Set<String> codeSet) {
        return shopMapper.selectSimpleByCodeSet(codeSet);
    }

    @Override
    public List<DataShopEntity> listByOffset(DataShopQueryListOffsetInputDto inputDto) {
        return shopMapper.listByOffset(inputDto);
    }

    @Override
    public List<DataShopListSimpleOutputDto> listAll(DataShopQueryListAllInputDto inputDto) {
        return shopMapper.listAll(inputDto);
    }

    @Override
    public Page<DataShopEntity> selectPage(DataShopQueryPageInputDto inputDto) {
        IPage<DataShopEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return shopMapper.selectPage(inputDto, page);
    }

    @Override
    public Page<DataShopEntity> pageCollectShop(DataSuperShopListCollectShopInputDto inputDto) {
        IPage<DataShopEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return shopMapper.pageCollectShop(inputDto, page);
    }

    @Override
    public List<DataShopEntity> listForExport(DataShopExportInputDto inputDto) {
        return shopMapper.listForExport(inputDto);
    }

}
