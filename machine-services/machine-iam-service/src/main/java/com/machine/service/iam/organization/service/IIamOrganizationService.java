package com.machine.service.iam.organization.service;

import com.machine.client.iam.organization.dto.input.IamOrganizationCreateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateParentInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IIamOrganizationService {

    String create(IamOrganizationCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamOrganizationUpdateInputDto inputDto);

    int updateParent(IamOrganizationUpdateParentInputDto inputDto);

    IamOrganizationDetailOutputDto detail(IdRequest request);

    List<IamOrganizationListOutputDto> listAllByType(IamOrganizationTypeEnum organizationType);

    IamOrganizationTreeSimpleOutputDto treeAllSimple(IamOrganizationTypeEnum organizationType);

}
