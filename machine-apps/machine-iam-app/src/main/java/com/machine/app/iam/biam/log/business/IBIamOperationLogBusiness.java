package com.machine.app.iam.biam.log.business;

import com.machine.app.iam.biam.log.controller.vo.request.BIamOperationLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

/**
 * 操作日志业务接口。
 */
public interface IBIamOperationLogBusiness {

    /**
     * 操作日志详情
     */
    BIamOperationLogDetailResponseVo detail(IdRequest request);

    /**
     * 操作日志分页查询
     */
    PageResponse<BIamOperationLogExpandListResponseVo> pageExpand(BIamOperationLogQueryPageRequestVo request);
}
