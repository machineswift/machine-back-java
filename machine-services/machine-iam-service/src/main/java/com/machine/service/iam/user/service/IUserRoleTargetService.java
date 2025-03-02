package com.machine.service.iam.user.service;

import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoBindFranchiseeShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoUnbindFranchiseeShopInputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IUserRoleTargetService {

    boolean bindFranchiseeShop(IamUserRoleInfoBindFranchiseeShopInputDto inputDto);

    boolean unbindFranchiseeShop(IamUserRoleInfoUnbindFranchiseeShopInputDto inputDto);

    List<String> listUserIdByOrganizationIdSet(IdSetRequest request);

    List<IamUserRoleTargetListOutputDto> listByUserId(IdRequest request);

    List<IamUserRoleTargetListOutputDto> listByUserIdSet(IdSetRequest request);

    List<IamUserRoleTargetListOutputDto> listOrganizationShopByRoleIdSet(IdSetRequest request);

    List<IamUserRoleTargetListOutputDto> listByCondition(IamUserRoleTargetQueryListInputDto inputDto);

}
