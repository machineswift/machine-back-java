package com.machine.service.iam.biam.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.user.dto.input.*;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBIamUserService {

    String create(BIamUserCreateInputDto inputDto);

    int update(BIamUserUpdateInputDto inputDto);

    int updateStatus(BIamUserUpdateStatusInputDto inputDto);

    int updatePhone(BIamUserUpdatePhoneInputDto inputDto);

    int updatePassword(BIamUserUpdatePasswordInputDto dto);

    void updatePermission(BIamUserUpdatePermissionInputDto inputDto);

    int countNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto);

    BIamUserDetailOutputDto detail(IdRequest request);

    BIamUserAuthDetailOutputDto detailAuth(IdRequest request);

    BIamUserDto getByUserId(String userId);

    BIamUserDto getByUsername(String username);

    BIamUserDto getByThirdPartyUuid(BIamAuth2SourceEnum source,
                                    String thirdPartyUuid);

    BIamUserDto getByPhone(String phone);

    Set<String> getIdByRoleIdSet(IdSetRequest request);

    Set<String> getIdByShopIdSet(IdSetRequest request);

    Set<String> getIdByOrganizationIdSet(IdSetRequest request);

    List<String> listNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto);

    Map<String, BIamUserDetailOutputDto> mapByUserIdSet(IdSetRequest request);

    List<BIamUserListOutputDto> listByOffset(BIamUserQueryListOffsetInputDto inputDto);

    Page<BIamUserListOutputDto> selectPage(BIamUserQueryPageInputDto inputDto);

    String exportUser(BIamUserExportInputDto inputDto);

}
