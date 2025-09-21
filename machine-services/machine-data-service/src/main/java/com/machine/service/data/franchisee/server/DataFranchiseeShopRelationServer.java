package com.machine.service.data.franchisee.server;

import com.machine.client.data.franchisee.IDataFranchiseeShopRelationClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.service.IDataFranchiseeShopRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/franchisee_shop_relation")
public class DataFranchiseeShopRelationServer implements IDataFranchiseeShopRelationClient {

    @Autowired
    private IDataFranchiseeShopRelationService franchiseeShopRelationService;

    @Override
    @PostMapping("get_by_shopId")
    public DataFranchiseeShopRelationOutputDto getByShopId(@RequestBody @Validated IdRequest request) {
        return franchiseeShopRelationService.getByShopId(request);
    }

}
