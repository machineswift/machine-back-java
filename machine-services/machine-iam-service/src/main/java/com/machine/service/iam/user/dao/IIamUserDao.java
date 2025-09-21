package com.machine.service.iam.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;

import java.util.List;
import java.util.Set;

public interface IIamUserDao {

    String insert(IamUserEntity entity);

    int updateStatus(String userId,
                     StatusEnum status);

    int updatePhone(String userId,
                    String phone);

    int updatePassword(String userId,
                       String password);

    int update(IamUserEntity entity);

    int countNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto);

    IamUserEntity getById(String userId);

    IamUserEntity getByUsername(String username);

    IamUserEntity getByThirdPartyUuid(IamAuth2SourceEnum source,
                                      String thirdPartyUuid);

    IamUserEntity getByCode(String code);

    IamUserEntity getByPhone(String phone);

    List<String> listIdByShopIdSet(Set<String> shopIdSet);

    List<String> listNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto);

    List<IamUserEntity> selectByIdSet(Set<String> idSet);

    List<IamUserEntity> listByOffset(IamUserQueryListOffsetInputDto inputDto);

    Page<IamUserEntity> selectPage(IamUserQueryPageInputDto inputDto);

    Page<IamUserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<IamUserEntity> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<IamUserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);

    List<IamUserEntity> listShopUser4Export(IamUserExportInputDto inputDto);

}
