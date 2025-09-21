package com.machine.service.data.employee.dao;

import com.machine.client.data.employee.dto.input.DataShopEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.service.data.employee.dao.mapper.entity.DataShopEmployeeEntity;

import java.util.List;
import java.util.Set;

public interface IDataShopEmployeeDao {

    String insert(DataShopEmployeeEntity entity);

    int updateStatus(String id,
                     DataShopEmployeeStatusEnum employeeStatus);

    int update(DataShopEmployeeEntity entity);

    int updateIdentityCard(String shopEmployeeId,
                           SystemStorageTypeEnum persistenceStatus,
                           IdentityCardDto identityCard);

    int updateHealthCertificate(String shopEmployeeId,
                                SystemStorageTypeEnum persistenceStatus,
                                HealthCertificateDto healthCertificate);

    DataShopEmployeeEntity getById(String id);

    DataShopEmployeeEntity getByUserId(String userId);

    IdentityCardDto getIdentityCard(String shopEmployeeId,
                                    SystemStorageTypeEnum persistenceStatus);

    HealthCertificateDto getHealthCertificated(String shopEmployeeId,
                                               SystemStorageTypeEnum persistenceStatus);

    Set<String> listUserIdByCondition(DataShopEmployeeListUserIdInputDto inputDto);

    List<DataShopEmployeeEntity> listByOffset(DataShopEmployeeQueryListOffsetInputDto inputDto);

}
