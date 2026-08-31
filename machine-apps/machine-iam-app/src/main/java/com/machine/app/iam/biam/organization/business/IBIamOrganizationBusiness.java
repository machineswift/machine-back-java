package com.machine.app.iam.biam.organization.business;

import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationCreateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationQueryTreeRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateParentRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.request.BIamOrganizationUpdateRequestVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationDetailResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationExpandTreeResponseVo;
import com.machine.app.iam.biam.organization.controller.vo.response.BIamOrganizationWithShopTreeResponseVo;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IBIamOrganizationBusiness {

    String create(BIamOrganizationCreateRequestVo request);

    void delete(IdRequest request);

    void update(BIamOrganizationUpdateRequestVo request);

    void updateParent(BIamOrganizationUpdateParentRequestVo request);

    BIamOrganizationDetailResponseVo detail(IdRequest request);

    BIamOrganizationTreeSimpleOutputDto treeSimple(BIamOrganizationQueryTreeRequestVo request);

    BIamOrganizationExpandTreeResponseVo treeExpand(BIamOrganizationQueryTreeRequestVo request);

    BIamOrganizationWithShopTreeResponseVo treeExpandWithShop(BIamOrganizationQueryTreeRequestVo request);

}
