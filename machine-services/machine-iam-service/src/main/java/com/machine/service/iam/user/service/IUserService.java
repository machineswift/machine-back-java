package com.machine.service.iam.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IUserService {

    String createCompanyUser(IamCompanyUserCreateInputDto inputDto);

    String createShopUser(IamShopUserCreateInputDto inputDto);

    String createShopUser4Openapi(IamShopUserCreate4OpenapiInputDto inputDto);

    String createSupplierUser(IamSupplierUserCreateInputDto inputDto);

    String createFranchiseeUser(IamFranchiseeUserCreateInputDto inputDto);

    int updateStatus(IamUserUpdateStatusInputDto inputDto);

    int updatePhone(IamUserUpdatePhoneInputDto inputDto);

    int updatePassword(IamUserUpdatePasswordInputDto dto);

    int updateCompanyUser(IamCompanyUserUpdateInputDto inputDto);

    int updateCompanyUser4BeiSen(IamCompanyUserUpdate4BeiSenInputDto inputDto);

    int updateShopUser(IamShopUserUpdateInputDto inputDto);

    void updateShopUserRole(IamShopUserUpdateRoleInputDto inputDto);

    int updateSupplierUser(IamSupplierUserUpdateInputDto inputDto);

    int updateUserRole(IamCompanyUserUpdateInputDto inputDto);

    int countNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto);

    UserDetailOutputDto detail(IdRequest request);

    UserAuthDetailOutputDto detailAuth(IdRequest request);

    UserDto getByUserId(String userId);

    UserDto getByUserName(String userName);

    UserDto getByPhone(String phone);

    List<String> listNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto);

    Map<String, UserDetailOutputDto> mapByUserIdSet(IdSetRequest request);

    List<UserListOutputDto> listByOffset(IamUserQueryListOffsetInputDto inputDto);

    Page<UserListOutputDto> page(IamUserQueryPageInputDto inputDto);

    Page<UserListOutputDto> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<UserListOutputDto> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<UserListOutputDto> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);

    MaterialDto exportShopUser(IamShopUserExportInputDto inputDto);

}
