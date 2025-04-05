package com.machine.service.data.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeListOutputDto;
import com.machine.sdk.common.envm.data.CertificateStatusEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.employee.dao.IShopEmployeeDao;
import com.machine.service.data.employee.dao.mapper.entity.ShopEmployeeEntity;
import com.machine.service.data.employee.service.IShopEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ShopEmployeeServiceImpl implements IShopEmployeeService {

    @Autowired
    private IShopEmployeeDao shopEmployeeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataShopEmployeeCreateInputDto inputDto) {
        ShopEmployeeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ShopEmployeeEntity.class);
        String shopEmployeeId = shopEmployeeDao.insert(insertEntity);
        //身份证
        shopEmployeeDao.updateIdentityCard(shopEmployeeId, DataPersistenceStatusEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证
        shopEmployeeDao.updateHealthCertificate(shopEmployeeId, DataPersistenceStatusEnum.PERMANENT, inputDto.getHealthCertificate());
        return shopEmployeeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByUserId(DataShopEmployeeUpdateInputDto inputDto) {
        ShopEmployeeEntity dbEntity = shopEmployeeDao.getByUserId(inputDto.getUserId());
        if (null == dbEntity) {
            return 0;
        }
        //身份证
        shopEmployeeDao.updateIdentityCard(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证
        shopEmployeeDao.updateHealthCertificate(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT, inputDto.getHealthCertificate());
        return 1;
    }

    @Override
    public ShopEmployeeDetailOutputDto detail(IdRequest request) {
        ShopEmployeeEntity dbEntity = shopEmployeeDao.getById(request.getId());
        if (null == dbEntity) {
            return null;
        }

        ShopEmployeeDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), ShopEmployeeDetailOutputDto.class);
        //身份证
        outputDto.setIdentityCard(shopEmployeeDao.getIdentityCard(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));
        //健康证
        outputDto.setHealthCertificate(shopEmployeeDao.getHealthCertificated(dbEntity.getId(), DataPersistenceStatusEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public ShopEmployeeDetailOutputDto getByUserId(IdRequest request) {
        ShopEmployeeEntity dbEntity = shopEmployeeDao.getByUserId(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), ShopEmployeeDetailOutputDto.class);
    }

    @Override
    public OpenapiShopEmployeeIdentityCardOutputDto identityCard(IdRequest request) {
        OpenapiShopEmployeeIdentityCardOutputDto outputDto = new OpenapiShopEmployeeIdentityCardOutputDto();
        outputDto.setTemporaryIdentityCard(shopEmployeeDao.getIdentityCard(request.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentIdentityCard(shopEmployeeDao.getIdentityCard(request.getId(), DataPersistenceStatusEnum.PERMANENT));

        { //证件状态
            IdentityCardDto permanent = outputDto.getPermanentIdentityCard();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public OpenapiShopEmployeeHealthCertificateOutputDto healthCertificate(IdRequest request) {
        OpenapiShopEmployeeHealthCertificateOutputDto outputDto = new OpenapiShopEmployeeHealthCertificateOutputDto();
        outputDto.setTemporaryHealthCertificate(shopEmployeeDao.getHealthCertificated(request.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentHealthCertificate(shopEmployeeDao.getHealthCertificated(request.getId(), DataPersistenceStatusEnum.PERMANENT));

        { //证件状态
            HealthCertificateDto permanent = outputDto.getPermanentHealthCertificate();
            if (null == permanent) {
                outputDto.setCertificateStatus(CertificateStatusEnum.MISSING);
            } else {
                outputDto.setCertificateStatus(CertificateStatusEnum.NORMAL);
            }
        }
        return outputDto;
    }

    @Override
    public List<ShopEmployeeListOutputDto> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto) {
        List<ShopEmployeeEntity> entityList = shopEmployeeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ShopEmployeeListOutputDto.class);
    }
}
