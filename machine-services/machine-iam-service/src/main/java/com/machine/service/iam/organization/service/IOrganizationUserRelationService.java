package com.machine.service.iam.organization.service;


import com.machine.client.iam.organization.dto.output.IamOrganizationUserRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.Map;

public interface IOrganizationUserRelationService {

    Map<String, String> mapByOrganizationIdSet(IdSetRequest request);

    IamOrganizationUserRelationOutputDto selectByOrganizationId(IdRequest request);

}
