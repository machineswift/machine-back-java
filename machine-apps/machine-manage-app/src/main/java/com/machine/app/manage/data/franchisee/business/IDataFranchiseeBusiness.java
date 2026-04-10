package com.machine.app.manage.data.franchisee.business;

import com.machine.app.manage.data.franchisee.controller.vo.response.FranchiseeDetailResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

public interface IDataFranchiseeBusiness {

    FranchiseeDetailResponseVo detail(IdRequest request);
}
