package com.machine.service.data.shop.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.client.data.shop.dto.input.*;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopFrontPhotoDto;
import com.machine.sdk.self.domain.data.shop.DataShopDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.external.dao.IExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;
import com.machine.service.data.shop.dao.IShopDao;
import com.machine.service.data.shop.dao.mapper.IShopMapper;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ShopDaoImpl implements IShopDao {

    private static final String TABLE_NAME = "t_shop";

    /**
     * 地址信息
     */
    private static final String ADDRESS_PERMANENT_COLUMN = "address_permanent";

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
    private IShopMapper shopMapper;

    @Autowired
    private IExternalFieldDataDao externalFieldDataDao;

    @Override
    public String insert(ShopEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_CREATE, new IdDto(entity.getId()));

        shopMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int updateBaseInfo(ShopEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_BASE, new IdDto(entity.getId()));

        return shopMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            ShopStatusEnum status) {
        ShopEntity entity = new ShopEntity();
        entity.setId(id);
        entity.setStatus(status);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_STATUS,
                new DataShopDto(entity.getId(), status));

        return shopMapper.updateById(entity);
    }

    @Override
    public void updateAddress(String id,
                              AddressInfoDto addressInfo) {

        //删除扩展数据
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(ADDRESS_PERMANENT_COLUMN);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        //新增扩展数据
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(ADDRESS_PERMANENT_COLUMN);
        insertEntity.setDataId(id);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(addressInfo));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_UPDATE_ADDRESS, new IdDto(id));
    }

    @Override
    public int updateShopBusinessLicense(String id,
                                         DataPersistenceStatusEnum persistenceStatus,
                                         ShopBusinessLicenseDto businessLicense) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == businessLicense) {
                return 0;
            }
            fieldName = SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
                                         DataPersistenceStatusEnum persistenceStatus,
                                         FoodBusinessLicenseDto businessLicense) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == businessLicense) {
                return 0;
            }
            fieldName = FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = FOOD_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
                                          DataPersistenceStatusEnum persistenceStatus,
                                          DisinfectingContractDto contract) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == contract) {
                return 0;
            }
            fieldName = DISINFECTING_CONTRACT_TEMPORARY_COLUMN;
        } else {
            fieldName = DISINFECTING_CONTRACT_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(DISINFECTING_CONTRACT_PERMANENT_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
                                    DataPersistenceStatusEnum persistenceStatus,
                                    ShopFrontPhotoDto shopFrontPhoto) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == shopFrontPhoto) {
                return 0;
            }
            fieldName = SHOP_FRONT_PHOTO_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_FRONT_PHOTO_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(id);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(SHOP_FRONT_PHOTO_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(id);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
    public AddressInfoDto getAddress(String id) {
        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(ADDRESS_PERMANENT_COLUMN);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), AddressInfoDto.class);
    }

    @Override
    public ShopBusinessLicenseDto shopBusinessLicense(String id,
                                                      DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = SHOP_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), ShopBusinessLicenseDto.class);
    }

    @Override
    public FoodBusinessLicenseDto foodBusinessLicense(String id, DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = FOOD_BUSINESS_LICENSE_TEMPORARY_COLUMN;
        } else {
            fieldName = FOOD_BUSINESS_LICENSE_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), FoodBusinessLicenseDto.class);
    }

    @Override
    public DisinfectingContractDto disinfectingContract(String id,
                                                        DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = DISINFECTING_CONTRACT_TEMPORARY_COLUMN;
        } else {
            fieldName = DISINFECTING_CONTRACT_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), DisinfectingContractDto.class);
    }

    @Override
    public ShopFrontPhotoDto shopFrontPhoto(String id,
                                            DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = SHOP_FRONT_PHOTO_TEMPORARY_COLUMN;
        } else {
            fieldName = SHOP_FRONT_PHOTO_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(id);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), ShopFrontPhotoDto.class);
    }

    @Override
    public ShopEntity getById(String id) {
        return shopMapper.selectById(id);
    }

    @Override
    public ShopEntity getByCode(String shopCode) {
        Wrapper<ShopEntity> queryWrapper = new LambdaQueryWrapper<ShopEntity>()
                .eq(ShopEntity::getCode, shopCode);
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
    public List<ShopEntity> selectByIdSet(Set<String> idSet) {
        Wrapper<ShopEntity> queryWrapper = new LambdaQueryWrapper<ShopEntity>()
                .in(ShopEntity::getId, idSet);
        return shopMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopEntity> selectByCodeSet(Set<String> codeSet) {
        Wrapper<ShopEntity> queryWrapper = new LambdaQueryWrapper<ShopEntity>()
                .in(ShopEntity::getCode, codeSet);
        return shopMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopEntity> listByOffset(DataShopQueryListOffsetInputDto inputDto) {
        return shopMapper.listByOffset(inputDto);
    }

    @Override
    public List<ShopEntity> listAll(DataShopQueryListAllInputDto inputDto) {
        return shopMapper.listAll(inputDto);
    }

    @Override
    public Page<ShopEntity> pageShop(DataShopQueryPageInputDto inputDto) {
        IPage<ShopEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return shopMapper.pageShop(inputDto, page);
    }

    @Override
    public Page<ShopEntity> pageCollectShop(SuperShopListCollectShopInputDto inputDto) {
        inputDto.setUserId(AppContext.getContext().getUserId());
        IPage<ShopEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return shopMapper.pageCollectShop(inputDto, page);
    }

}
