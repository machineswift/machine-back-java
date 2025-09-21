package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.user.dto.output.IamUserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/user",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamUserClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamUserCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated IamUserUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated IamUserUpdateStatusInputDto inputDto);

    @PostMapping("update_phone")
    int updatePhone(@RequestBody @Validated IamUserUpdatePhoneInputDto inputDto);

    @PostMapping("update_password")
    int updatePassword(@RequestBody @Validated IamUserUpdatePasswordInputDto inputDto);

    @PostMapping("update_permission")
    void updatePermission(@RequestBody @Validated IamUserUpdatePermissionInputDto inputDto);

    @PostMapping("count_not_bind_Organization")
    int countNotBindOrganization(@RequestBody @Validated IamDataUserNotBindOrganizationInputDto inputDto);

    @GetMapping("get_by_userId")
    IamUserDto getByUserId(@RequestParam("userId") String userId);

    @GetMapping("get_by_username")
    IamUserDto getByUserName(@RequestParam("username") String username);

    @GetMapping("get_by_thirdPartyUuid")
    IamUserDto getByThirdPartyUuid(@RequestParam("source") IamAuth2SourceEnum source,
                                   @RequestParam("thirdPartyUuid") String thirdPartyUuid);

    @GetMapping("get_by_phone")
    IamUserDto getByPhone(@RequestParam("phone") String phone);

    @PostMapping("detail")
    IamUserDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_auth")
    IamUserAuthDetailOutputDto detailAuth(@RequestBody @Validated IdRequest request);

    @PostMapping("getId_by_roleIdSet")
    Set<String> getIdByRoleIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("getId_by_shopIdSet")
    Set<String> getIdByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("getId_by_organizationIdSet")
    Set<String> getIdByOrganizationIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_not_bind_Organization")
    List<String> listNotBindOrganization(@RequestBody @Validated IamDataUserNotBindOrganizationInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, IamUserDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<IamUserListOutputDto> listByOffset(@RequestBody @Validated IamUserQueryListOffsetInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<IamUserListOutputDto> selectPage(@RequestBody @Validated IamUserQueryPageInputDto inputDto);

    @PostMapping("export_user")
    String exportUser(@RequestBody @Validated IamUserExportInputDto inputDto);

}
