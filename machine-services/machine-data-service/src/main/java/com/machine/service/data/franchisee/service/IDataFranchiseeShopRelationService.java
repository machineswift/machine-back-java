package com.machine.service.data.franchisee.service;

import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IDataFranchiseeShopRelationService {

    DataFranchiseeShopRelationOutputDto getByShopId(IdRequest request);

}
