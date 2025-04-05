package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.iam.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/user")
public class IamUserServer implements IIamUserClient {

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("create_company_user")
    public String createCompanyUser(@RequestBody @Validated IamCompanyUserCreateInputDto inputDto) {
        log.info("创建公司员工，inputDto:{}", JSONUtil.toJsonStr(inputDto));
        return userService.createCompanyUser(inputDto);
    }

    @Override
    @PostMapping("create_shop_user")
    public String createShopUser(@RequestBody @Validated IamShopUserCreateInputDto inputDto) {
        log.info("创建门店员工，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.createShopUser(inputDto);
    }

    @Override
    @PostMapping("create_supplier_user")
    public String createSupplierUser(IamSupplierUserCreateInputDto inputDto) {
        log.info("创建供应商用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.createSupplierUser(inputDto);
    }

    @Override
    @PostMapping("create_franchisee_user")
    public String createFranchiseeUser(IamFranchiseeUserCreateInputDto inputDto) {
        log.info("创建加盟商用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.createFranchiseeUser(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated IamUserUpdateStatusInputDto inputDto) {
        log.info("修改员工状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_phone")
    public int updatePhone(IamUserUpdatePhoneInputDto inputDto) {
        log.info("修改员工手机号，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updatePhone(inputDto);
    }

    @Override
    @PostMapping("update_password")
    public int updatePassword(@RequestBody @Validated IamUserUpdatePasswordInputDto inputDto) {
        log.info("修改用户密码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updatePassword(inputDto);
    }

    @Override
    @PostMapping("update_company_user")
    public int updateCompanyUser(@RequestBody @Validated IamCompanyUserUpdateInputDto inputDto) {
        log.info("修改公司员工，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateCompanyUser(inputDto);
    }

    @Override
    @PostMapping("update_company_user_4_bei_sen")
    public int updateCompanyUser4BeiSen(@RequestBody @Validated IamCompanyUserUpdate4BeiSenInputDto inputDto) {
        log.info("修改公司员工(北森)，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateCompanyUser4BeiSen(inputDto);
    }

    @Override
    @PostMapping("update_shop_user")
    public int updateShopUser(@RequestBody @Validated IamShopUserUpdateInputDto inputDto) {
        log.info("修改门店员工，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateShopUser(inputDto);
    }

    @Override
    @PostMapping("update_supplier_user")
    public int updateSupplierUser(@RequestBody @Validated IamSupplierUserUpdateInputDto inputDto) {
        log.info("修改供应商用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateSupplierUser(inputDto);
    }

    @Override
    @PostMapping("update_user_role")
    public void updateUserRole(@RequestBody @Validated IamCompanyUserUpdateInputDto inputDto) {
        log.info("修改用户角色，inputDto={}", JSONUtil.toJsonStr(inputDto));
        userService.updateUserRole(inputDto);
    }

    @Override
    @PostMapping("count_not_bind_Organization")
    public int countNotBindOrganization(@RequestBody @Validated DataUserNotBindOrganizationInputDto inputDto) {
        return userService.countNotBindOrganization(inputDto);
    }

    @Override
    @GetMapping("get_by_userId")
    public UserDto getByUserId(@RequestParam("userId") String userId) {
        return userService.getByUserId(userId);
    }

    @Override
    @GetMapping("get_by_userName")
    public UserDto getByUserName(@RequestParam("userName") String userName) {
        return userService.getByUserName(userName);
    }

    @Override
    @GetMapping("get_by_phone")
    public UserDto getByPhone(@RequestParam("phone") String phone) {
        return userService.getByPhone(phone);
    }

    @Override
    @PostMapping("detail")
    public UserDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userService.detail(request);
    }

    @Override
    @PostMapping("detail_auth")
    public UserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request) {
        return userService.detailAuth(request);
    }

    @Override
    @PostMapping("list_not_bind_Organization")
    public List<String> listNotBindOrganization(@RequestBody @Validated DataUserNotBindOrganizationInputDto inputDto) {
        return userService.listNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, UserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return userService.mapByUserIdSet(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<UserListOutputDto> listByOffset(@RequestBody @Validated IamUserQueryListOffsetInputDto inputDto) {
        return userService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("page_user")
    public PageResponse<UserListOutputDto> pageUser(@RequestBody @Validated IamUserQueryPageInputDto inputDto) {
        Page<UserListOutputDto> pageResult = userService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_company_user")
    public PageResponse<UserListOutputDto> pageCompanyUser(@RequestBody @Validated IamCompanyUserQueryPageInputDto inputDto) {
        Page<UserListOutputDto> pageResult = userService.pageCompany(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_shop_user")
    public PageResponse<UserListOutputDto> pageShopUser(@RequestBody @Validated IamShopUserQueryPageInputDto inputDto) {
        Page<UserListOutputDto> pageResult = userService.pageShop(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_supplier_user")
    public PageResponse<UserListOutputDto> pageSupplierUser(@RequestBody @Validated IamSupplierUserQueryPageInputDto inputDto) {
        Page<UserListOutputDto> pageResult = userService.pageSupplier(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("export_shop_user")
    public MaterialDto exportShopUser(@RequestBody @Validated IamShopUserExportInputDto inputDto) {
        log.info("导出门店员工，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.exportShopUser(inputDto);
    }
}
