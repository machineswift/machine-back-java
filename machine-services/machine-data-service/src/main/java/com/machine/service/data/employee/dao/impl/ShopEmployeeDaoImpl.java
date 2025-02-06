package com.machine.service.data.employee.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.self.domain.data.employee.DataShopEmployeeDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.employee.dao.IShopEmployeeDao;
import com.machine.service.data.employee.dao.mapper.ShopEmployeeMapper;
import com.machine.service.data.employee.dao.mapper.entity.ShopEmployeeEntity;
import com.machine.service.data.external.dao.IExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopEmployeeDaoImpl implements IShopEmployeeDao {

    private static final String TABLE_NAME = "t_shop_employee";
    /**
     * 身份证
     */
    private static final String IDENTITY_CARD_TEMPORARY_COLUMN = "identity_card_temporary";
    private static final String IDENTITY_CARD_PERMANENT_COLUMN = "identity_card_permanent";
    /**
     * 健康证
     */
    private static final String HEALTH_CERTIFICATE_TEMPORARY_COLUMN = "health_certificate_temporary";
    private static final String HEALTH_CERTIFICATE_PERMANENT_COLUMN = "health_certificate_permanent";


    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private ShopEmployeeMapper shopEmployeeMapper;

    @Autowired
    private IExternalFieldDataDao externalFieldDataDao;


    @Override
    public String insert(ShopEmployeeEntity entity) {
        shopEmployeeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_CREATE,
                new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int updateStatus(String id,
                            ShopEmployeeStatusEnum employeeStatus) {
        ShopEmployeeEntity entity = new ShopEmployeeEntity();
        entity.setId(id);
        entity.setEmployeeStatus(employeeStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_STATUS,
                new DataShopEmployeeDto(entity.getId(), employeeStatus));

        return shopEmployeeMapper.updateById(entity);
    }

    @Override
    public int update(ShopEmployeeEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_BASE,
                new IdDto(entity.getId()));

        return shopEmployeeMapper.updateById(entity);
    }

    @Override
    public int updateIdentityCard(String shopEmployeeId,
                                  DataPersistenceStatusEnum persistenceStatus,
                                  IdentityCardDto identityCard) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == identityCard) {
                return 0;
            }
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(shopEmployeeId);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(IDENTITY_CARD_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(shopEmployeeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(shopEmployeeId);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(identityCard));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_IDENTITY_CARD,
                new IdDto(shopEmployeeId));
        return 1;
    }

    @Override
    public int updateHealthCertificate(String shopEmployeeId,
                                       DataPersistenceStatusEnum persistenceStatus,
                                       HealthCertificateDto healthCertificate) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            if (null == healthCertificate) {
                return 0;
            }
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(shopEmployeeId);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(HEALTH_CERTIFICATE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(shopEmployeeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(shopEmployeeId);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(healthCertificate));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_HEALTH_CERTIFICATE,
                new IdDto(shopEmployeeId));
        return 1;
    }

    @Override
    public ShopEmployeeEntity getById(String id) {
        return shopEmployeeMapper.selectById(id);
    }

    @Override
    public ShopEmployeeEntity getByUserId(String userId) {
        Wrapper<ShopEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<ShopEmployeeEntity>()
                .eq(ShopEmployeeEntity::getUserId, userId);
        return shopEmployeeMapper.selectOne(deleteWrapper);
    }

    @Override
    public IdentityCardDto getIdentityCard(String shopEmployeeId,
                                           DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(shopEmployeeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), IdentityCardDto.class);
    }

    @Override
    public HealthCertificateDto getHealthCertificated(String shopEmployeeId,
                                                      DataPersistenceStatusEnum persistenceStatus) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        ExternalFieldDataGetValueInputDto inputDto = new ExternalFieldDataGetValueInputDto();
        inputDto.setTableName(TABLE_NAME);
        inputDto.setFieldName(fieldName);
        inputDto.setDataId(shopEmployeeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), HealthCertificateDto.class);
    }

    @Override
    public List<ShopEmployeeEntity> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto) {
        return shopEmployeeMapper.listByOffset(inputDto);
    }

}
