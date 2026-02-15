package com.machine.service.data.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeListOutputDto;
import com.machine.sdk.common.envm.base.StorageTypeEnum;
import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.employee.dao.IDataShopEmployeeDao;
import com.machine.service.data.employee.dao.mapper.entity.DataShopEmployeeEntity;
import com.machine.service.data.employee.service.IDataShopEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DataShopEmployeeServiceImpl implements IDataShopEmployeeService {

    @Autowired
    private IDataShopEmployeeDao shopEmployeeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataShopEmployeeCreateInputDto inputDto) {
        DataShopEmployeeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataShopEmployeeEntity.class);
        String shopEmployeeId = shopEmployeeDao.insert(insertEntity);
        //身份证
        shopEmployeeDao.updateIdentityCard(shopEmployeeId, StorageTypeEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证
        shopEmployeeDao.updateHealthCertificate(shopEmployeeId, StorageTypeEnum.PERMANENT, inputDto.getHealthCertificate());
        return shopEmployeeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByUserId(DataShopEmployeeUpdateInputDto inputDto) {
        DataShopEmployeeEntity dbEntity = shopEmployeeDao.getByUserId(inputDto.getUserId());
        if (null == dbEntity) {
            return 0;
        }
        //身份证
        shopEmployeeDao.updateIdentityCard(dbEntity.getId(), StorageTypeEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证
        shopEmployeeDao.updateHealthCertificate(dbEntity.getId(), StorageTypeEnum.PERMANENT, inputDto.getHealthCertificate());
        return 1;
    }

    @Override
    public DataShopEmployeeDetailOutputDto detail(IdRequest request) {
        DataShopEmployeeEntity dbEntity = shopEmployeeDao.getById(request.getId());
        if (null == dbEntity) {
            return null;
        }

        DataShopEmployeeDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataShopEmployeeDetailOutputDto.class);
        //身份证
        outputDto.setIdentityCard(shopEmployeeDao.getIdentityCard(dbEntity.getId(), StorageTypeEnum.PERMANENT));
        //健康证
        outputDto.setHealthCertificate(shopEmployeeDao.getHealthCertificated(dbEntity.getId(), StorageTypeEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public DataShopEmployeeDetailOutputDto getByUserId(IdRequest request) {
        DataShopEmployeeEntity dbEntity = shopEmployeeDao.getByUserId(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataShopEmployeeDetailOutputDto.class);
    }

    @Override
    public OpenapiShopEmployeeIdentityCardOutputDto identityCard(IdRequest request) {
        OpenapiShopEmployeeIdentityCardOutputDto outputDto = new OpenapiShopEmployeeIdentityCardOutputDto();
        outputDto.setTemporaryIdentityCard(shopEmployeeDao.getIdentityCard(request.getId(), StorageTypeEnum.TEMPORARY));
        outputDto.setPermanentIdentityCard(shopEmployeeDao.getIdentityCard(request.getId(), StorageTypeEnum.PERMANENT));

        { //证件状态
            IdentityCardDto permanent = outputDto.getPermanentIdentityCard();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(IdRequest request) {
        OpenapiShopEmployeeHealthCertificateOutputDto outputDto = new OpenapiShopEmployeeHealthCertificateOutputDto();
        outputDto.setTemporaryHealthCertificate(shopEmployeeDao.getHealthCertificated(request.getId(), StorageTypeEnum.TEMPORARY));
        outputDto.setPermanentHealthCertificate(shopEmployeeDao.getHealthCertificated(request.getId(), StorageTypeEnum.PERMANENT));

        { //证件状态
            HealthCertificateDto permanent = outputDto.getPermanentHealthCertificate();
            if (null == permanent) {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(DataCertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public Set<String> listUserIdByCondition(DataShopEmployeeListUserIdInputDto inputDto) {
        return shopEmployeeDao.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataShopEmployeeListOutputDto> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto) {
        List<DataShopEmployeeEntity> entityList = shopEmployeeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataShopEmployeeListOutputDto.class);
    }
}
