package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamUserLoginLogBusiness {

    IamUserLoginLogDetailResponseVo detail(IdRequest request);

    PageResponse<IamUserLoginLogExpandListResponseVo> pageExpand(IamUserLoginLogQueryPageRequestVo request);

}
