package com.machine.app.openapi.iam.organization.business;

import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.request.OpenApiOrganizationRootIdRequestVo;
import com.machine.app.openapi.iam.organization.controller.vo.response.OpenApiOrganizationDetailResponseVo;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;

import java.util.List;

public interface IOpenApiOrganizationBusiness {

    String rootId(OpenApiOrganizationRootIdRequestVo request);

    OpenApiOrganizationDetailResponseVo detail(OpenApiOrganizationIdRequestVo request);

    List<String> listSubId(OpenApiOrganizationIdRequestVo request);

    List<IamOrganizationTreeSimpleOutputDto> listSub(OpenApiOrganizationIdRequestVo request);

    List<String> listParentByTarget(OpenApiOrganizationIdRequestVo request);

}
