package com.machine.service.iam.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.user.dto.output.IamUserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IIamUserService {

    String create(IamUserCreateInputDto inputDto);

    int update(IamUserUpdateInputDto inputDto);

    int updateStatus(IamUserUpdateStatusInputDto inputDto);

    int updatePhone(IamUserUpdatePhoneInputDto inputDto);

    int updatePassword(IamUserUpdatePasswordInputDto dto);

    void updatePermission(IamUserUpdatePermissionInputDto inputDto);

    int countNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto);

    IamUserDetailOutputDto detail(IdRequest request);

    IamUserAuthDetailOutputDto detailAuth(IdRequest request);

    IamUserDto getByUserId(String userId);

    IamUserDto getByUsername(String username);

    IamUserDto getByThirdPartyUuid(IamAuth2SourceEnum source,
                                   String thirdPartyUuid);

    IamUserDto getByPhone(String phone);

    Set<String> getIdByRoleIdSet(IdSetRequest request);

    Set<String> getIdByShopIdSet(IdSetRequest request);

    Set<String> getIdByOrganizationIdSet(IdSetRequest request);

    List<String> listNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto);

    Map<String, IamUserDetailOutputDto> mapByUserIdSet(IdSetRequest request);

    List<IamUserListOutputDto> listByOffset(IamUserQueryListOffsetInputDto inputDto);

    Page<IamUserListOutputDto> selectPage(IamUserQueryPageInputDto inputDto);

    String exportUser(IamUserExportInputDto inputDto);

}
