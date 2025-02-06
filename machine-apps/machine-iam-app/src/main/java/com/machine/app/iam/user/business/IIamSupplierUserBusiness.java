package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.user.controller.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.user.controller.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamSupplierUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamSupplierUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamSupplierUserBusiness {

    String create(IamSupplierUserCreateRequestVo request);

    void update(IamSupplierUserUpdateRequestVo request);

    IamSupplierUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(IamSupplierUserQueryPageExpandRequestVo request);

}
