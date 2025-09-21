package com.machine.service.iam.user.service;

import com.machine.client.iam.user.dto.output.IamUserOrganizationRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IIamUserOrganizationRelationService {

    List<IamUserOrganizationRelationOutputDto> listByUserId(IdRequest request);

    List<IamUserOrganizationRelationOutputDto> listByOrganizationIdSet(IdSetRequest request);

}
