package com.machine.client.iam.userbk;

import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.client.iam.userbk.dto.input.*;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/userbk",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserBkClient {

    @PostMapping("create_company_user")
    String createCompanyUser(@RequestBody @Validated IamCompanyUserCreateInputDto inputDto);

    @PostMapping("create_shop_user")
    String createShopUser(@RequestBody @Validated IamShopUserCreateInputDto inputDto);

    @PostMapping("create_supplier_user")
    String createSupplierUser(@RequestBody @Validated IamSupplierUserCreateInputDto inputDto);

    @PostMapping("create_franchisee_user")
    String createFranchiseeUser(@RequestBody @Validated IamFranchiseeUserCreateInputDto inputDto);

    @PostMapping("update_company_user_4_bei_sen")
    int updateCompanyUser4BeiSen(@RequestBody @Validated IamCompanyUserUpdate4BeiSenInputDto inputDto);

    @PostMapping("update_shop_user")
    int updateShopUser(@RequestBody @Validated IamShopUserUpdateInputDto inputDto);

    @PostMapping("update_supplier_user")
    int updateSupplierUser(@RequestBody @Validated IamSupplierUserUpdateInputDto inputDto);

    @PostMapping("page_company_user")
    PageResponse<IamUserListOutputDto> pageCompanyUser(@RequestBody @Validated IamCompanyUserQueryPageInputDto inputDto);

    @PostMapping("page_shop_user")
    PageResponse<IamUserListOutputDto> pageShopUser(@RequestBody @Validated IamShopUserQueryPageInputDto inputDto);

    @PostMapping("page_supplier_user")
    PageResponse<IamUserListOutputDto> pageSupplierUser(@RequestBody @Validated IamSupplierUserQueryPageInputDto inputDto);

}
