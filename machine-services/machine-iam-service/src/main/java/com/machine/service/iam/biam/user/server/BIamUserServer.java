package com.machine.service.iam.biam.user.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.input.*;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.sdk.base.annotation.SkipUserIdCheck;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.iam.biam.user.service.IBIamUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user")
public class BIamUserServer implements IBIamUserClient {

    @Autowired
    private IBIamUserService userService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamUserCreateInputDto inputDto) {
        log.info("创建用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated BIamUserUpdateInputDto inputDto) {
        log.info("修改用户， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated BIamUserUpdateStatusInputDto inputDto) {
        log.info("修改员工状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_phone")
    public int updatePhone(BIamUserUpdatePhoneInputDto inputDto) {
        log.info("修改员工手机号，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updatePhone(inputDto);
    }

    @Override
    @PostMapping("update_password")
    public int updatePassword(@RequestBody @Validated BIamUserUpdatePasswordInputDto inputDto) {
        log.info("修改用户密码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.updatePassword(inputDto);
    }

    @Override
    @PostMapping("update_permission")
    public void updatePermission(BIamUserUpdatePermissionInputDto inputDto) {
        log.info("修改用户权限，inputDto={}", JSONUtil.toJsonStr(inputDto));
        userService.updatePermission(inputDto);
    }

    @Override
    @PostMapping("count_not_bind_Organization")
    public int countNotBindOrganization(@RequestBody @Validated BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userService.countNotBindOrganization(inputDto);
    }

    @Override
    @GetMapping("get_by_userId")
    public BIamUserDto getByUserId(@RequestParam("userId") String userId) {
        return userService.getByUserId(userId);
    }

    @Override
    @SkipUserIdCheck
    @GetMapping("get_by_username")
    public BIamUserDto getByUserName(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @Override
    @SkipUserIdCheck
    @GetMapping("get_by_phone")
    public BIamUserDto getByPhone(@RequestParam("phone") String phone) {
        return userService.getByPhone(phone);
    }

    @Override
    @SkipUserIdCheck
    @GetMapping("get_by_thirdPartyUuid")
    public BIamUserDto getByThirdPartyUuid(@RequestParam("source") BIamAuth2SourceEnum source,
                                           @RequestParam("thirdPartyUuid") String thirdPartyUuid) {
        return userService.getByThirdPartyUuid(source, thirdPartyUuid);
    }

    @Override
    @PostMapping("detail")
    public BIamUserDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return userService.detail(request);
    }

    @Override
    @SkipUserIdCheck
    @PostMapping("detail_auth")
    public BIamUserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request) {
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
    public List<String> listNotBindOrganization(@RequestBody @Validated BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userService.listNotBindOrganization(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, BIamUserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return userService.mapByUserIdSet(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<BIamUserListOutputDto> listByOffset(@RequestBody @Validated BIamUserQueryListOffsetInputDto inputDto) {
        return userService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<BIamUserListOutputDto> selectPage(@RequestBody @Validated BIamUserQueryPageInputDto inputDto) {
        Page<BIamUserListOutputDto> pageResult = userService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("export_user")
    public String exportUser(@RequestBody @Validated BIamUserExportInputDto inputDto) {
        log.info("导出用户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return userService.exportUser(inputDto);
    }
}
