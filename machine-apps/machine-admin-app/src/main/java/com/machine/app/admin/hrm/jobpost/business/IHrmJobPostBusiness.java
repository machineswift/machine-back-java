package com.machine.app.admin.hrm.jobpost.business;

import com.machine.app.admin.hrm.jobpost.controller.vo.request.HrmJobPostListSimpleRequestVo;
import com.machine.app.admin.hrm.jobpost.controller.vo.response.HrmJobPostListSimpleResponseVo;
import com.machine.sdk.base.model.response.PageResponse;

public interface IHrmJobPostBusiness {

    PageResponse<HrmJobPostListSimpleResponseVo> pageSimple(HrmJobPostListSimpleRequestVo request);

}
