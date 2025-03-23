package com.machine.service.iam.user.service;


import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.Map;

public interface IUserOrganizationRelationService {

    Map<String, String> mapLeaderByOrganizationIdSet(IdSetRequest request);

    IamUserOrganizationRelationOutputDto selectLeaderByOrganizationId(IdRequest request);

}
