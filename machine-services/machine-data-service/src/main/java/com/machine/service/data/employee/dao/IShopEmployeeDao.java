package com.machine.service.data.employee.dao;

import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.service.data.employee.dao.mapper.entity.ShopEmployeeEntity;

import java.util.List;

public interface IShopEmployeeDao {

    String insert(ShopEmployeeEntity entity);

    int updateStatus(String id,
                     ShopEmployeeStatusEnum employeeStatus);

    int update(ShopEmployeeEntity entity);

    int updateIdentityCard(String shopEmployeeId,
                           DataPersistenceStatusEnum persistenceStatus,
                           IdentityCardDto identityCard);

    int updateHealthCertificate(String shopEmployeeId,
                                DataPersistenceStatusEnum persistenceStatus,
                                HealthCertificateDto healthCertificate);

    ShopEmployeeEntity getById(String id);

    ShopEmployeeEntity getByUserId(String userId);

    IdentityCardDto getIdentityCard(String shopEmployeeId,
                                    DataPersistenceStatusEnum persistenceStatus);

    HealthCertificateDto getHealthCertificated(String shopEmployeeId,
                                               DataPersistenceStatusEnum persistenceStatus);

    List<ShopEmployeeEntity> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto);

}
