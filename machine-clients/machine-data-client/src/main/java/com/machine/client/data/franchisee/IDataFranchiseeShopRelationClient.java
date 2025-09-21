package com.machine.client.data.franchisee;

import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/franchisee_shop_relation",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataFranchiseeShopRelationClient {

    @PostMapping("get_by_shopId")
    DataFranchiseeShopRelationOutputDto getByShopId(@RequestBody @Validated IdRequest request);

}
