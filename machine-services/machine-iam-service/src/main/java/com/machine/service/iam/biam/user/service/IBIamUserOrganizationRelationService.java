package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;

public interface IBIamUserOrganizationRelationService {

    List<BIamUserOrganizationRelationOutputDto> listByUserId(IdRequest request);

    List<BIamUserOrganizationRelationOutputDto> listByOrganizationIdSet(IdSetRequest request);

}
