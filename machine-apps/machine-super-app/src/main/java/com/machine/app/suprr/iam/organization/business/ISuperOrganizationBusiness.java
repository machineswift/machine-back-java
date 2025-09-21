package com.machine.app.suprr.iam.organization.business;

import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeAllRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.request.SupeOrganizationTreeRequestVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeExpandSelfResponseVo;
import com.machine.app.suprr.iam.organization.controller.vo.response.SuperOrganizationTreeSimpleSelfResponseVo;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;

public interface ISuperOrganizationBusiness {

    IamOrganizationTreeSimpleOutputDto treeAllSimple(SupeOrganizationTreeAllRequestVo request);

    SuperOrganizationTreeSimpleSelfResponseVo treeSelfSimple(SupeOrganizationTreeRequestVo request);

    SuperOrganizationTreeExpandSelfResponseVo treeSelfExpand(SupeOrganizationTreeRequestVo request);

}
