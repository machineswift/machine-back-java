package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IBIamUserRoleRelationService {

    List<BIamUserRoleRelationListOutputDto> listByUserId(IdRequest request);
    
    List<BIamUserRoleRelationListOutputDto> listByIdSet(IdSetRequest request);

    List<BIamUserRoleRelationListOutputDto> listByRoleIdSet(IdSetRequest request);

    List<BIamUserRoleRelationListOutputDto> listByUserIdSet(IdSetRequest request);

    Map<String, Integer> countUserByRoleIdSet(IdSetRequest request);
}
