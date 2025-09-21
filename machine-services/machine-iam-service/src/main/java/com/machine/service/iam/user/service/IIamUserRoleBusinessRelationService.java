package com.machine.service.iam.user.service;

import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IIamUserRoleBusinessRelationService {

    boolean bindFranchiseeShop(IamUserRoleInfoFranchiseeBindShopInputDto inputDto);

    boolean unbindFranchiseeShop(IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto);

    List<IamUserRoleBusinessRelationListOutputDto> listByShopIdSet(IdSetRequest request);

    List<IamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(IdSetRequest request);
}
