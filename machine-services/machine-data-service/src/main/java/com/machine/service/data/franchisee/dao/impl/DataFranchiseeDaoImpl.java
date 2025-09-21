package com.machine.service.data.franchisee.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.sdk.common.envm.DataCertificateTypeEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.external.dao.IDataExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;
import com.machine.service.data.franchisee.dao.IDataFranchiseeDao;
import com.machine.service.data.franchisee.dao.mapper.DataFranchiseeMapper;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataFranchiseeDaoImpl implements IDataFranchiseeDao {

    private static final String TABLE_NAME = "t_franchisee";
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
    private DataFranchiseeMapper dataFranchiseeMapper;

    @Autowired
    private IDataExternalFieldDataDao externalFieldDataDao;

    @Override
    public String insert(DataFranchiseeEntity entity) {
        dataFranchiseeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_CREATE,
                new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int update(DataFranchiseeEntity entity) {

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UPDATE_BASE,
                new IdDto(entity.getId()));

        return dataFranchiseeMapper.updateById(entity);
    }

    @Override
    public int updateIdentityCard(String franchiseeId,
                                   SystemStorageTypeEnum persistenceStatus,
                                   IdentityCardDto identityCard) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(franchiseeId);
        externalFieldDataDao.delete(deleteEntity);

        if (SystemStorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(IDENTITY_CARD_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(franchiseeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(franchiseeId);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(identityCard));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UPDATE_IDENTITY_CARD,
                new IdDto(franchiseeId));
        return 1;
    }

    @Override
    public int updateHealthCertificate(String franchiseeId,
                                       SystemStorageTypeEnum persistenceStatus,
                                       HealthCertificateDto healthCertificate) {
        String fieldName;
        if (SystemStorageTypeEnum.TEMPORARY == persistenceStatus) {
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        //删除
        DataExternalFieldDataEntity deleteEntity = new DataExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(franchiseeId);
        externalFieldDataDao.delete(deleteEntity);

        if (SystemStorageTypeEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            DataExternalFieldDataEntity deleteTemporaryEntity = new DataExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(HEALTH_CERTIFICATE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(franchiseeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        DataExternalFieldDataEntity insertEntity = new DataExternalFieldDataEntity();
        insertEntity.setTableName(TABLE_NAME);
        insertEntity.setFieldName(fieldName);
        insertEntity.setDataId(franchiseeId);
        insertEntity.setExternalValue(JSONUtil.toJsonStr(healthCertificate));
        externalFieldDataDao.insert(insertEntity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UPDATE_HEALTH_CERTIFICATE,
                new IdDto(franchiseeId));
        return 1;
    }

    @Override
    public DataFranchiseeEntity getById(String id) {
        return dataFranchiseeMapper.selectById(id);
    }

    @Override
    public DataFranchiseeEntity getByCode(String code) {
        Wrapper<DataFranchiseeEntity> queryWrapper = new LambdaQueryWrapper<DataFranchiseeEntity>()
                .eq(DataFranchiseeEntity::getCode, code);
        return dataFranchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public DataFranchiseeEntity getByUserId(String userId) {
        Wrapper<DataFranchiseeEntity> queryWrapper = new LambdaQueryWrapper<DataFranchiseeEntity>()
                .eq(DataFranchiseeEntity::getUserId, userId);
        return dataFranchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public DataFranchiseeEntity getByCertificate(DataCertificateTypeEnum certificateType,
                                                 String certificateNumber) {
        Wrapper<DataFranchiseeEntity> queryWrapper = new LambdaQueryWrapper<DataFranchiseeEntity>()
                .eq(DataFranchiseeEntity::getCertificateType, certificateType)
                .eq(DataFranchiseeEntity::getCertificateNumber, certificateNumber);
        return dataFranchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public IdentityCardDto getIdentityCard(String franchiseeId,
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
        inputDto.setDataId(franchiseeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), IdentityCardDto.class);
    }

    @Override
    public HealthCertificateDto getHealthCertificated(String franchiseeId,
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
        inputDto.setDataId(franchiseeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), HealthCertificateDto.class);
    }

    @Override
    public Set<String> listUserIdByCondition(DataFranchiseeListUserIdInputDto inputDto) {
        return dataFranchiseeMapper.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataFranchiseeEntity> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto) {
        return dataFranchiseeMapper.listByOffset(inputDto);
    }
}
