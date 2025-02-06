package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogPageResponseVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamUserLoginLogBusiness {

    PageResponse<IamUserLoginLogPageResponseVo> page(IamUserLoginLogQueryPageRequestVo request);

}
