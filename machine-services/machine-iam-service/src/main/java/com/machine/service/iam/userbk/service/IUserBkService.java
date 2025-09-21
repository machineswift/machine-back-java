package com.machine.service.iam.userbk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.client.iam.userbk.dto.input.*;

public interface IUserBkService {

    String createCompanyUser(IamCompanyUserCreateInputDto inputDto);

    String createShopUser(IamShopUserCreateInputDto inputDto);

    String createSupplierUser(IamSupplierUserCreateInputDto inputDto);

    String createFranchiseeUser(IamFranchiseeUserCreateInputDto inputDto);

    int updateCompanyUser4BeiSen(IamCompanyUserUpdate4BeiSenInputDto inputDto);

    int updateShopUser(IamShopUserUpdateInputDto inputDto);

    int updateSupplierUser(IamSupplierUserUpdateInputDto inputDto);

    Page<IamUserListOutputDto> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<IamUserListOutputDto> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<IamUserListOutputDto> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);
}
