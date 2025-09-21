package com.machine.app.manage.hrm.jobpost.business;

import com.machine.app.manage.hrm.jobpost.controller.vo.request.*;
import com.machine.app.manage.hrm.jobpost.controller.vo.response.HrmJobPostListSimpleResponseVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IHrmJobPostBusiness {

    PageResponse<HrmJobPostListSimpleResponseVo> pageSimple(HrmJobPostListSimpleRequestVo request);

}
