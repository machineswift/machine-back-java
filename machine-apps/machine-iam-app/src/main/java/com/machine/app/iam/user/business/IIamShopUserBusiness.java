package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamShopUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamShopUserExportRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamShopUserBusiness {

    String create(IamShopUserCreateRequestVo request);

    void update(IamShopUserUpdateRequestVo request);

    IamShopUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamShopUserExpandListResponseVo> pageExpand(IamShopUserQueryPageExpandRequestVo request);

    void export(IamShopUserExportRequestVo request);
}
