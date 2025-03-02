package com.machine.service.iam.organization.service;

import com.machine.client.iam.organization.dto.input.IamOrganizationCreateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationDetailByNameInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateParentInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IOrganizationService {

    String create(IamOrganizationCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(IamOrganizationUpdateInputDto inputDto);

    int updateParent(IamOrganizationUpdateParentInputDto inputDto);

    IamOrganizationDetailOutputDto detail(IdRequest request);

    IamOrganizationDetailOutputDto detailByName(IamOrganizationDetailByNameInputDto inputDto);

    List<IamOrganizationListOutputDto> listAllByType(OrganizationTypeEnum organizationType);

    IamOrganizationTreeSimpleOutputDto treeAllSimple(OrganizationTypeEnum organizationType);

}
