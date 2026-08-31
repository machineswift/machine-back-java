package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.input.*;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.annotation.SkipUserIdCheck;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamUserCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated BIamUserUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated BIamUserUpdateStatusInputDto inputDto);

    @PostMapping("update_phone")
    int updatePhone(@RequestBody @Validated BIamUserUpdatePhoneInputDto inputDto);

    @PostMapping("update_password")
    int updatePassword(@RequestBody @Validated BIamUserUpdatePasswordInputDto inputDto);

    @PostMapping("update_permission")
    void updatePermission(@RequestBody @Validated BIamUserUpdatePermissionInputDto inputDto);

    @PostMapping("count_not_bind_Organization")
    int countNotBindOrganization(@RequestBody @Validated BIamDataUserNotBindOrganizationInputDto inputDto);

    @GetMapping("get_by_userId")
    BIamUserDto getByUserId(@RequestParam("userId") String userId);

    @SkipUserIdCheck
    @GetMapping("get_by_username")
    BIamUserDto getByUserName(@RequestParam("username") String username);

    @SkipUserIdCheck
    @GetMapping("get_by_phone")
    BIamUserDto getByPhone(@RequestParam("phone") String phone);

    @SkipUserIdCheck
    @GetMapping("get_by_thirdPartyUuid")
    BIamUserDto getByThirdPartyUuid(@RequestParam("source") BIamAuth2SourceEnum source,
                                    @RequestParam("thirdPartyUuid") String thirdPartyUuid);

    @PostMapping("detail")
    BIamUserDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @SkipUserIdCheck
    @PostMapping("detail_auth")
    BIamUserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request);

    @PostMapping("getId_by_roleIdSet")
    Set<String> getIdByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("getId_by_shopIdSet")
    Set<String> getIdByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("getId_by_organizationIdSet")
    Set<String> getIdByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_not_bind_Organization")
    List<String> listNotBindOrganization(@RequestBody @Validated BIamDataUserNotBindOrganizationInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, BIamUserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<BIamUserListOutputDto> listByOffset(@RequestBody @Validated BIamUserQueryListOffsetInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<BIamUserListOutputDto> selectPage(@RequestBody @Validated BIamUserQueryPageInputDto inputDto);

    @PostMapping("export_user")
    String exportUser(@RequestBody @Validated BIamUserExportInputDto inputDto);

}
