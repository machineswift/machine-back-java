package com.machine.app.partner.iam.organization.business;

import com.machine.app.partner.iam.organization.controller.vo.request.SupeOrganizationTreeAllRequestVo;
import com.machine.app.partner.iam.organization.controller.vo.request.SupeOrganizationTreeRequestVo;
import com.machine.app.partner.iam.organization.controller.vo.response.SuperOrganizationTreeExpandSelfResponseVo;
import com.machine.app.partner.iam.organization.controller.vo.response.SuperOrganizationTreeSimpleSelfResponseVo;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;

public interface ISuperOrganizationBusiness {

    BIamOrganizationTreeSimpleOutputDto treeAllSimple(SupeOrganizationTreeAllRequestVo request);

    SuperOrganizationTreeSimpleSelfResponseVo treeSelfSimple(SupeOrganizationTreeRequestVo request);

    SuperOrganizationTreeExpandSelfResponseVo treeSelfExpand(SupeOrganizationTreeRequestVo request);

}
