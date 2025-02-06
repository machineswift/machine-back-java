package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamCompanyUserBusiness {

    void update(IamCompanyUserUpdateRequestVo request);

    IamCompanyUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(IamCompanyUserQueryPageExpandRequestVo request);
}
