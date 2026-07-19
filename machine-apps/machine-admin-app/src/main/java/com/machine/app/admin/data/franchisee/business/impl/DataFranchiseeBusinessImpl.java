package com.machine.app.admin.data.franchisee.business.impl;

import com.machine.app.admin.data.franchisee.business.IDataFranchiseeBusiness;
import com.machine.app.admin.data.franchisee.controller.vo.response.FranchiseeDetailResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataFranchiseeBusinessImpl implements IDataFranchiseeBusiness {

    @Override
    public FranchiseeDetailResponseVo detail(IdRequest request) {
        return null;
    }
}
