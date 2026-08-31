package com.machine.app.iam.biam.userbk.business;

import com.machine.app.iam.biam.userbk.vo.request.IamCompanyUserQueryPageExpandRequestVo;
import com.machine.app.iam.biam.userbk.vo.response.IamCompanyUserDetailResponseVo;
import com.machine.app.iam.biam.userbk.vo.response.IamCompanyUserExpandListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IIamCompanyUserBusiness {

    IamCompanyUserDetailResponseVo detail(IdRequest request);

    PageResponse<IamCompanyUserExpandListResponseVo> pageExpand(IamCompanyUserQueryPageExpandRequestVo request);
}
