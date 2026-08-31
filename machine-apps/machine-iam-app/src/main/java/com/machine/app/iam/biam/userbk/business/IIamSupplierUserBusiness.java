package com.machine.app.iam.biam.userbk.business;

import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamSupplierUserDetailResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamSupplierUserExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IIamSupplierUserBusiness {

    String create(IamSupplierUserCreateRequestVo request);

    void update(IamSupplierUserUpdateRequestVo request);

    IamSupplierUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(IamSupplierUserQueryPageExpandRequestVo request);

}
