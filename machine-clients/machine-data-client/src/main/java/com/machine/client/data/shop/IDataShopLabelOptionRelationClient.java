package com.machine.client.data.shop;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/shop_label_option_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataShopLabelOptionRelationClient {

    @PostMapping("list_label_optionId_by_shopId")
    List<String> listLabelOptionIdByShopId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_shopId_by_label_optionId")
    List<String> listShopIdByLabelOptionId(@RequestBody @Validated IdRequest request);


    @PostMapping("list_shopId_by_label_optionIds")
    List<String> listShopIdByLabelOptionIds(@RequestBody @Validated IdSetRequest request);

    /**
     * 查询标签绑定的门店数量
     */
    @PostMapping("count_shop_by_label_optionIds")
    Map<String, Long> countShopByLabelOptionIds(@RequestBody @Validated IdSetRequest request);

    /**
     * <门店ID,标签选项ID集合>
     */
    @PostMapping("map_label_optionId_by_shopIds")
    Map<String, List<String>> mapLabelOptionIdByShopIds(@RequestBody @Validated IdSetRequest request);

}



