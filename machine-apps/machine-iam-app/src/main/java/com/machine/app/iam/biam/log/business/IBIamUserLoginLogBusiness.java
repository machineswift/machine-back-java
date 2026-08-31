package com.machine.app.iam.biam.log.business;

import com.machine.app.iam.biam.log.controller.vo.request.BIamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IBIamUserLoginLogBusiness {

    BIamUserLoginLogDetailResponseVo detail(IdRequest request);

    PageResponse<BIamUserLoginLogExpandListResponseVo> pageExpand(BIamUserLoginLogQueryPageRequestVo request);

}
