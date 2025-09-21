package com.machine.service.iam.user.service;

import com.machine.client.iam.user.dto.output.IamUserRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IIamUserRoleRelationService {

    List<IamUserRoleRelationListOutputDto> listByUserId(IdRequest request);
    
    List<IamUserRoleRelationListOutputDto> listByIdSet(IdSetRequest request);

    List<IamUserRoleRelationListOutputDto> listByRoleIdSet(IdSetRequest request);

    List<IamUserRoleRelationListOutputDto> listByUserIdSet(IdSetRequest request);

    Map<String, Integer> countUserByRoleIdSet(IdSetRequest request);
}
