package com.machine.service.iam.biam.organization.service;

import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationCreateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateParentInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationListOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;

import java.util.List;

public interface IBIamOrganizationService {

    String create(BIamOrganizationCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(BIamOrganizationUpdateInputDto inputDto);

    int updateParent(BIamOrganizationUpdateParentInputDto inputDto);

    BIamOrganizationDetailOutputDto detail(IdRequest request);

    List<BIamOrganizationListOutputDto> listAllByType(BIamOrganizationTypeEnum type);

    Tuples.Tuple2<String, BIamOrganizationTreeSimpleOutputDto> treeAllSimple(BIamOrganizationTypeEnum type);

}
