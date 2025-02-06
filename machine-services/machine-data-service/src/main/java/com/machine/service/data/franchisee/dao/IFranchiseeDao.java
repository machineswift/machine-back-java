package com.machine.service.data.franchisee.dao;

import com.machine.client.data.franchisee.dto.input.DataFranchiseeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.CertificateTypeEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeEntity;

import java.util.List;

public interface IFranchiseeDao {

    String insert(FranchiseeEntity entity);

    int update(FranchiseeEntity entity);

    int updateIdentityCard(String franchiseeId,
                           DataPersistenceStatusEnum persistenceStatus,
                           IdentityCardDto identityCard);

    int updateHealthCertificate(String franchiseeId,
                                DataPersistenceStatusEnum persistenceStatus,
                                HealthCertificateDto healthCertificate);

    FranchiseeEntity getById(String id);

    FranchiseeEntity getByCode(String code);

    FranchiseeEntity getByUserId(String userId);

    FranchiseeEntity getByCertificate(CertificateTypeEnum certificateType,
                                      String certificateNumber);

    IdentityCardDto getIdentityCard(String franchiseeId,
                                    DataPersistenceStatusEnum persistenceStatus);

    HealthCertificateDto getHealthCertificated(String franchiseeId,
                                               DataPersistenceStatusEnum persistenceStatus);

    List<FranchiseeEntity> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto);

}
