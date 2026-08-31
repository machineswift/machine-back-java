package com.machine.service.iam.biam.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.dto.input.BIamDataUserNotBindOrganizationInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryListOffsetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamCompanyUserQueryPageInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserExportInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamShopUserQueryPageInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamSupplierUserQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserEntity;

import java.util.List;
import java.util.Set;

public interface IBIamUserDao {

    String insert(BIamUserEntity entity);

    int updateStatus(String userId,
                     StatusEnum status);

    int updatePhone(String userId,
                    String phone);

    int updatePassword(String userId,
                       String password);

    int update(BIamUserEntity entity);

    int countNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto);

    BIamUserEntity getById(String userId);

    BIamUserEntity getByUsername(String username);

    BIamUserEntity getByThirdPartyUuid(BIamAuth2SourceEnum source,
                                       String thirdPartyUuid);

    BIamUserEntity getByCode(String code);

    BIamUserEntity getByPhone(String phone);

    List<String> listIdByShopIdSet(Set<String> shopIdSet);

    List<String> listNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto);

    List<BIamUserEntity> selectByIdSet(Set<String> idSet);

    List<BIamUserEntity> listByOffset(BIamUserQueryListOffsetInputDto inputDto);

    Page<BIamUserEntity> selectPage(BIamUserQueryPageInputDto inputDto);

    Page<BIamUserEntity> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<BIamUserEntity> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<BIamUserEntity> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);

    List<BIamUserEntity> listShopUser4Export(BIamUserExportInputDto inputDto);

}
