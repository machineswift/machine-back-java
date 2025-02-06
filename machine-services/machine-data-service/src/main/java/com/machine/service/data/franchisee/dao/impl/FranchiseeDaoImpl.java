package com.machine.service.data.franchisee.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.CertificateTypeEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.external.dao.IExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;
import com.machine.service.data.franchisee.dao.IFranchiseeDao;
import com.machine.service.data.franchisee.dao.mapper.FranchiseeMapper;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FranchiseeDaoImpl implements IFranchiseeDao {

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
    private FranchiseeMapper franchiseeMapper;

    @Autowired
    private IExternalFieldDataDao externalFieldDataDao;

    @Override
    public String insert(FranchiseeEntity entity) {
        franchiseeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_CREATE,
                new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int update(FranchiseeEntity entity) {

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_FRANCHISEE_UPDATE_BASE,
                new IdDto(entity.getId()));

        return franchiseeMapper.updateById(entity);
    }

    @Override
    public int updateIdentityCard(String franchiseeId,
                                   DataPersistenceStatusEnum persistenceStatus,
                                   IdentityCardDto identityCard) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = IDENTITY_CARD_TEMPORARY_COLUMN;
        } else {
            fieldName = IDENTITY_CARD_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(franchiseeId);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(IDENTITY_CARD_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(franchiseeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
                                       DataPersistenceStatusEnum persistenceStatus,
                                       HealthCertificateDto healthCertificate) {
        String fieldName;
        if (DataPersistenceStatusEnum.TEMPORARY == persistenceStatus) {
            fieldName = HEALTH_CERTIFICATE_TEMPORARY_COLUMN;
        } else {
            fieldName = HEALTH_CERTIFICATE_PERMANENT_COLUMN;
        }

        //删除
        ExternalFieldDataEntity deleteEntity = new ExternalFieldDataEntity();
        deleteEntity.setTableName(TABLE_NAME);
        deleteEntity.setFieldName(fieldName);
        deleteEntity.setDataId(franchiseeId);
        externalFieldDataDao.delete(deleteEntity);

        if (DataPersistenceStatusEnum.PERMANENT == persistenceStatus) {
            //审核通过删除临时数据
            ExternalFieldDataEntity deleteTemporaryEntity = new ExternalFieldDataEntity();
            deleteTemporaryEntity.setTableName(TABLE_NAME);
            deleteTemporaryEntity.setFieldName(HEALTH_CERTIFICATE_TEMPORARY_COLUMN);
            deleteTemporaryEntity.setDataId(franchiseeId);
            externalFieldDataDao.delete(deleteTemporaryEntity);
        }

        //新增
        ExternalFieldDataEntity insertEntity = new ExternalFieldDataEntity();
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
    public FranchiseeEntity getById(String id) {
        return franchiseeMapper.selectById(id);
    }

    @Override
    public FranchiseeEntity getByCode(String code) {
        Wrapper<FranchiseeEntity> queryWrapper = new LambdaQueryWrapper<FranchiseeEntity>()
                .eq(FranchiseeEntity::getCode, code);
        return franchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public FranchiseeEntity getByUserId(String userId) {
        Wrapper<FranchiseeEntity> queryWrapper = new LambdaQueryWrapper<FranchiseeEntity>()
                .eq(FranchiseeEntity::getUserId, userId);
        return franchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public FranchiseeEntity getByCertificate(CertificateTypeEnum certificateType,
                                             String certificateNumber) {
        Wrapper<FranchiseeEntity> queryWrapper = new LambdaQueryWrapper<FranchiseeEntity>()
                .eq(FranchiseeEntity::getCertificateType, certificateType)
                .eq(FranchiseeEntity::getCertificateNumber, certificateNumber);
        return franchiseeMapper.selectOne(queryWrapper);
    }

    @Override
    public IdentityCardDto getIdentityCard(String franchiseeId,
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
        inputDto.setDataId(franchiseeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), IdentityCardDto.class);
    }

    @Override
    public HealthCertificateDto getHealthCertificated(String franchiseeId,
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
        inputDto.setDataId(franchiseeId);
        String value = externalFieldDataDao.getValue(inputDto);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(value), HealthCertificateDto.class);
    }

    @Override
    public List<FranchiseeEntity> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto) {
        return franchiseeMapper.listByOffset(inputDto);
    }
}
