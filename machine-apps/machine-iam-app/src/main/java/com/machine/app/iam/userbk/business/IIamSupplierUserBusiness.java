package com.machine.app.iam.userbk.business;

import com.machine.app.iam.userbk.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.userbk.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.userbk.vo.response.IamSupplierUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamSupplierUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamSupplierUserBusiness {

    String create(IamSupplierUserCreateRequestVo request);

    void update(IamSupplierUserUpdateRequestVo request);

    IamSupplierUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(IamSupplierUserQueryPageExpandRequestVo request);

}
