package com.machine.app.iam.userbk.business;

import com.machine.app.iam.userbk.vo.request.IamCompanyUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IIamCompanyUserBusiness {

    IamCompanyUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(IamCompanyUserQueryPageExpandRequestVo request);
}
