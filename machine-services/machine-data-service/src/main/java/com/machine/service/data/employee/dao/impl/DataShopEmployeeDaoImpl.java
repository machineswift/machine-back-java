package com.machine.service.data.employee.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.employee.dto.input.DataShopEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.self.domain.data.employee.DataShopEmployeeDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.employee.dao.IDataShopEmployeeDao;
import com.machine.service.data.employee.dao.mapper.DataShopEmployeeMapper;
import com.machine.service.data.employee.dao.mapper.entity.DataShopEmployeeEntity;
import com.machine.service.data.external.dao.IDataExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataShopEmployeeDaoImpl implements IDataShopEmployeeDao {

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
    private DataShopEmployeeMapper dataShopEmployeeMapper;

    @Autowired
    private IDataExternalFieldDataDao externalFieldDataDao;


    @Override
    public String insert(DataShopEmployeeEntity entity) {
        dataShopEmployeeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_CREATE,
                new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int updateStatus(String id,
                            DataShopEmployeeStatusEnum employeeStatus) {
        DataShopEmployeeEntity entity = new DataShopEmployeeEntity();
        entity.setId(id);
        entity.setEmployeeStatus(employeeStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_STATUS,
                new DataShopEmployeeDto(entity.getId(), employeeStatus));

        return dataShopEmployeeMapper.updateById(entity);
    }

    @Override
    public int update(DataShopEmployeeEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_EMPLOYEE_UPDATE_BASE,
                new IdDto(entity.getId()));

        return dataShopEmployeeMapper.updateById(entity);
    }

    @Override
    public int updateIdentityCard(String shopEmployeeId,
                                  SystemStorageTypeEnum persistenceStatus,
                                  IdentityCardDto identityCard) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == identityCard) {
                return 0;
            }
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(shopEmployeeId);
        externalFieldDataDao.delete(deleteEntity);

        if (SystemStorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(IDENTITY_CARD_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(shopEmployeeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
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
                                       SystemStorageTypeEnum persistenceStatus,
                                       HealthCertificateDto healthCertificate) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            if (null == healthCertificate) {
                return 0;
            }
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(shopEmployeeId);
        externalFieldDataDao.delete(deleteEntity);

        if (SystemStorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(HEALTH_CERTIFICATE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(shopEmployeeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
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
    public DataShopEmployeeEntity getById(String id) {
        return dataShopEmployeeMapper.selectById(id);
    }

    @Override
    public DataShopEmployeeEntity getByUserId(String userId) {
        Wrapper<DataShopEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<DataShopEmployeeEntity>()
                .eq(DataShopEmployeeEntity::getUserId, userId);
        return dataShopEmployeeMapper.selectOne(deleteWrapper);
    }

    @Override
    public IdentityCardDto getIdentityCard(String shopEmployeeId,
                                           SystemStorageTypeEnum persistenceStatus) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
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
                                                      SystemStorageTypeEnum persistenceStatus) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        DataExternalFieldDataGetValueInputDto inputDto = new DataExternalFieldDataGetValueInputDto();
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
    public Set<String> listUserIdByCondition(DataShopEmployeeListUserIdInputDto inputDto) {
        return dataShopEmployeeMapper.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataShopEmployeeEntity> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto) {
        return dataShopEmployeeMapper.listByOffset(inputDto);
    }

}
