package com.machine.app.iam.biam.log.business;

import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogDeleteRequestVo;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IBIamUserAccessLogBusiness {

    BIamUserAccessLogDetailResponseVo detail(IdRequest request);

    PageResponse<BIamUserAccessLogExpandListResponseVo> pageExpand(BIamUserAccessLogQueryPageRequestVo request);

    int delete(BIamUserAccessLogDeleteRequestVo request);

}
