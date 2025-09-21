package com.machine.service.iam.userbk.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.client.iam.userbk.IIamUserBkClient;
import com.machine.client.iam.userbk.dto.input.*;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.iam.userbk.service.IUserBkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/iam/userbk")
public class IamUserBkServer implements IIamUserBkClient {

    @Autowired
    private IUserBkService userService;

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
    @PostMapping("page_company_user")
    public PageResponse<IamUserListOutputDto> pageCompanyUser(@RequestBody @Validated IamCompanyUserQueryPageInputDto inputDto) {
        Page<IamUserListOutputDto> pageResult = userService.pageCompany(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_shop_user")
    public PageResponse<IamUserListOutputDto> pageShopUser(@RequestBody @Validated IamShopUserQueryPageInputDto inputDto) {
        Page<IamUserListOutputDto> pageResult = userService.pageShop(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("page_supplier_user")
    public PageResponse<IamUserListOutputDto> pageSupplierUser(@RequestBody @Validated IamSupplierUserQueryPageInputDto inputDto) {
        Page<IamUserListOutputDto> pageResult = userService.pageSupplier(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
