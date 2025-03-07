package com.machine.client.data.shop;

import com.machine.client.data.shop.dto.input.SuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.SuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/user_collect_shop",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataUserCollectShopClient {

    @PostMapping("collect_shop")
    void collectShop(@RequestBody @Validated SuperShopCollectIdInputDto inputDto);

    @PostMapping("list_collectedId_by_shopIdSet")
    List<String> listCollectedIdByShopIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("page_collect_shop")
    PageResponse<DataShopListOutputDto> pageCollectShop(@RequestBody @Validated SuperShopListCollectShopInputDto inputDto);

}
