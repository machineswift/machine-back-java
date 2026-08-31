package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IBIamUserRoleBusinessRelationService {

    boolean bindFranchiseeShop(IamUserRoleInfoFranchiseeBindShopInputDto inputDto);

    boolean unbindFranchiseeShop(IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto);

    List<BIamUserRoleBusinessRelationListOutputDto> listByShopIdSet(IdSetRequest request);

    List<BIamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(IdSetRequest request);
}
