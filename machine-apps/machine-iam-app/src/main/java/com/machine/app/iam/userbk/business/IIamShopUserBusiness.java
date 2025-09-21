package com.machine.app.iam.userbk.business;

import com.machine.app.iam.userbk.vo.request.IamShopUserCreateRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserUpdateRequestVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserExportRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamShopUserBusiness {

    String create(IamShopUserCreateRequestVo request);

    void update(IamShopUserUpdateRequestVo request);

    IamShopUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamShopUserExpandListResponseVo> pageExpand(IamShopUserQueryPageExpandRequestVo request);

    void export(IamShopUserExportRequestVo request);
}
