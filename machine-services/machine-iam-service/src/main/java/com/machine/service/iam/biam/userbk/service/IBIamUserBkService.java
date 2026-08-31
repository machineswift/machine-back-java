package com.machine.service.iam.biam.userbk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.client.iam.biam.userbk.dto.input.*;

public interface IBIamUserBkService {

    String createCompanyUser(IamCompanyUserCreateInputDto inputDto);

    String createShopUser(IamShopUserCreateInputDto inputDto);

    String createSupplierUser(IamSupplierUserCreateInputDto inputDto);

    String createFranchiseeUser(IamFranchiseeUserCreateInputDto inputDto);

    int updateCompanyUser4BeiSen(IamCompanyUserUpdate4BeiSenInputDto inputDto);

    int updateShopUser(IamShopUserUpdateInputDto inputDto);

    int updateSupplierUser(IamSupplierUserUpdateInputDto inputDto);

    Page<BIamUserListOutputDto> pageCompany(IamCompanyUserQueryPageInputDto inputDto);

    Page<BIamUserListOutputDto> pageShop(IamShopUserQueryPageInputDto inputDto);

    Page<BIamUserListOutputDto> pageSupplier(IamSupplierUserQueryPageInputDto inputDto);
}
