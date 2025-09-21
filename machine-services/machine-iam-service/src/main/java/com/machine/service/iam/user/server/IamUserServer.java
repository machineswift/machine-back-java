package com.machine.service.iam.user.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.user.dto.output.IamUserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.iam.user.service.IIamUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("server/iam/user")
public class IamUserServer implements IIamUserClient {

    @Autowired
    private IIamUserService userService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamUserCreateInputDto inputDto) {
        log.info("创建用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated IamUserUpdateInputDto inputDto) {
        log.info("修改用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.update(inputDto);
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
    @PostMapping("update_permission")
    public void updatePermission(IamUserUpdatePermissionInputDto inputDto) {
        log.info("修改用户权限，inputDto={}", JSONUtil.toJsonStr(inputDto));
         userService.updatePermission(inputDto);
    }

    @Override
    @PostMapping("count_not_bind_Organization")
    public int countNotBindOrganization(@RequestBody @Validated IamDataUserNotBindOrganizationInputDto inputDto) {
        return userService.countNotBindOrganization(inputDto);
    }

    @Override
    @GetMapping("get_by_userId")
    public IamUserDto getByUserId(@RequestParam("userId") String userId) {
        return userService.getByUserId(userId);
    }

    @Override
    @GetMapping("get_by_username")
    public IamUserDto getByUserName(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @Override
    @GetMapping("get_by_thirdPartyUuid")
    public IamUserDto getByThirdPartyUuid(@RequestParam("source") IamAuth2SourceEnum source,
                                          @RequestParam("thirdPartyUuid") String thirdPartyUuid) {
        return userService.getByThirdPartyUuid(source,thirdPartyUuid);
    }

    @Override
    @GetMapping("get_by_phone")
    public IamUserDto getByPhone(@RequestParam("phone") String phone) {
        return userService.getByPhone(phone);
    }

    @Override
    @PostMapping("detail")
    public IamUserDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userService.detail(request);
    }

    @Override
    @PostMapping("detail_auth")
    public IamUserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request) {
        return userService.detailAuth(request);
    }

    @Override
    @PostMapping("getId_by_roleIdSet")
    public Set<String> getIdByRoleIdSet(@RequestBody @Validated IdSetRequest request) {
        return userService.getIdByRoleIdSet(request);
    }

    @Override
    @PostMapping("getId_by_shopIdSet")
    public Set<String> getIdByShopIdSet(IdSetRequest request) {
        return userService.getIdByShopIdSet(request);
    }

    @Override
    @PostMapping("getId_by_organizationIdSet")
    public Set<String> getIdByOrganizationIdSet(IdSetRequest request) {
        return userService.getIdByOrganizationIdSet(request);
    }

    @Override
    @PostMapping("list_not_bind_Organization")
    public List<String> listNotBindOrganization(@RequestBody @Validated IamDataUserNotBindOrganizationInputDto inputDto) {
        return userService.listNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, IamUserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return userService.mapByUserIdSet(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<IamUserListOutputDto> listByOffset(@RequestBody @Validated IamUserQueryListOffsetInputDto inputDto) {
        return userService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<IamUserListOutputDto> selectPage(@RequestBody @Validated IamUserQueryPageInputDto inputDto) {
        Page<IamUserListOutputDto> pageResult = userService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("export_user")
    public String exportUser(@RequestBody @Validated IamUserExportInputDto inputDto) {
        log.info("导出用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.exportUser(inputDto);
    }
}
