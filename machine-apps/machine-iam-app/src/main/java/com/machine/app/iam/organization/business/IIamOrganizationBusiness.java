package com.machine.app.iam.organization.business;

import com.machine.app.iam.organization.controller.vo.request.*;
import com.machine.app.iam.organization.controller.vo.response.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IIamOrganizationBusiness {

    String create(IamOrganizationCreateRequestVo request);

    void delete(IdRequest request);

    void update(IamOrganizationUpdateRequestVo request);

    void updateParent(IamOrganizationUpdateParentRequestVo request);

    IamOrganizationDetailResponseVo detail(IdRequest request);

    IamOrganizationTreeSimpleOutputDto treeSimple(IamOrganizationQueryTreeRequestVo request);

    IamOrganizationExpandTreeResponseVo treeExpand(IamOrganizationQueryTreeRequestVo request);

    IamOrganizationWithShopTreeResponseVo treeExpandWithShop(IamOrganizationQueryTreeRequestVo request);

}
