package com.machine.service.data.franchisee.dao;

import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.sdk.common.envm.DataCertificateTypeEnum;
import com.machine.sdk.common.envm.base.StorageTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeEntity;

import java.util.List;
import java.util.Set;

public interface IDataFranchiseeDao {

    String insert(DataFranchiseeEntity entity);

    int update(DataFranchiseeEntity entity);

    int updateIdentityCard(String franchiseeId,
                           StorageTypeEnum persistenceStatus,
                           IdentityCardDto identityCard);

    int updateHealthCertificate(String franchiseeId,
                                StorageTypeEnum persistenceStatus,
                                HealthCertificateDto healthCertificate);

    DataFranchiseeEntity getById(String id);

    DataFranchiseeEntity getByCode(String code);

    DataFranchiseeEntity getByUserId(String userId);

    DataFranchiseeEntity getByCertificate(DataCertificateTypeEnum certificateType,
                                          String certificateNumber);

    IdentityCardDto getIdentityCard(String franchiseeId,
                                    StorageTypeEnum persistenceStatus);

    HealthCertificateDto getHealthCertificated(String franchiseeId,
                                               StorageTypeEnum persistenceStatus);

    Set<String> listUserIdByCondition(DataFranchiseeListUserIdInputDto inputDto);

    List<DataFranchiseeEntity> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto);

}
