package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserClient {

    @PostMapping("create_company_user")
    String createCompanyUser(@RequestBody @Validated IamCompanyUserCreateInputDto inputDto);

    @PostMapping("create_shop_user")
    String createShopUser(@RequestBody @Validated IamShopUserCreateInputDto inputDto);

    @PostMapping("create_shop_user_4_openapi")
    String createShopUser4Openapi(@RequestBody @Validated IamShopUserCreate4OpenapiInputDto inputDto);

    @PostMapping("create_supplier_user")
    String createSupplierUser(@RequestBody @Validated IamSupplierUserCreateInputDto inputDto);

    @PostMapping("create_franchisee_user")
    String createFranchiseeUser(@RequestBody @Validated IamFranchiseeUserCreateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated IamUserUpdateStatusInputDto inputDto);

    @PostMapping("update_phone")
    int updatePhone(@RequestBody @Validated IamUserUpdatePhoneInputDto inputDto);

    @PostMapping("update_password")
    int updatePassword(@RequestBody @Validated IamUserUpdatePasswordInputDto inputDto);

    @PostMapping("update_company_user")
    int updateCompanyUser(@RequestBody @Validated IamCompanyUserUpdateInputDto inputDto);

    @PostMapping("update_company_user_4_bei_sen")
    int updateCompanyUser4BeiSen(@RequestBody @Validated IamCompanyUserUpdate4BeiSenInputDto inputDto);

    @PostMapping("update_shop_user")
    int updateShopUser(@RequestBody @Validated IamShopUserUpdateInputDto inputDto);

    @PostMapping("update_shop_user_role")
    void updateShopUserRole(@RequestBody @Validated IamShopUserUpdateRoleInputDto inputDto);

    @PostMapping("update_supplier_user")
    int updateSupplierUser(@RequestBody @Validated IamSupplierUserUpdateInputDto inputDto);

    @PostMapping("update_user_role")
    int updateUserRole(@RequestBody @Validated IamCompanyUserUpdateInputDto inputDto);

    @PostMapping("count_not_bind_Organization")
    int countNotBindOrganization(@RequestBody @Validated DataUserNotBindOrganizationInputDto inputDto);

    @GetMapping("get_by_userId")
    UserDto getByUserId(@RequestParam("userId") String userId);

    @GetMapping("get_by_userName")
    UserDto getByUserName(@RequestParam("userName") String userName);

    @GetMapping("get_by_phone")
    UserDto getByPhone(@RequestParam("phone") String phone);

    @PostMapping("detail")
    UserDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_auth")
    UserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request);

    @PostMapping("list_not_bind_Organization")
    List<String> listNotBindOrganization(@RequestBody @Validated DataUserNotBindOrganizationInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, UserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<UserListOutputDto> listByOffset(@RequestBody @Validated IamUserQueryListOffsetInputDto inputDto);

    @PostMapping("page_user")
    PageResponse<UserListOutputDto> pageUser(@RequestBody @Validated IamUserQueryPageInputDto inputDto);

    @PostMapping("page_company_user")
    PageResponse<UserListOutputDto> pageCompanyUser(@RequestBody @Validated IamCompanyUserQueryPageInputDto inputDto);

    @PostMapping("page_shop_user")
    PageResponse<UserListOutputDto> pageShopUser(@RequestBody @Validated IamShopUserQueryPageInputDto inputDto);

    @PostMapping("page_supplier_user")
    PageResponse<UserListOutputDto> pageSupplierUser(@RequestBody @Validated IamSupplierUserQueryPageInputDto inputDto);

    @PostMapping("export_shop_user")
    MaterialDto exportShopUser(@RequestBody @Validated IamShopUserExportInputDto inputDto);
}
