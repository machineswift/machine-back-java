package com.machine.service.data.shop.server;

import com.machine.client.data.shop.IDataShopLabelOptionRelationClient;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.service.IDataShopLabelOptionRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/shop_label_option_relation")
public class DataShopDataLabelOptionRelationServer implements IDataShopLabelOptionRelationClient {

    @Autowired
    private IDataShopLabelOptionRelationService shopLabelOptionRelationService;

    @Override
    @PostMapping("list_label_optionId_by_shopId")
    public List<String> listLabelOptionIdByShopId(@RequestBody @Validated IdRequest request) {
        return shopLabelOptionRelationService.listLabelOptionIdByShopId(request);
    }

    @Override
    @PostMapping("list_shopId_by_label_optionId")
    public List<String> listShopIdByLabelOptionId(@RequestBody @Validated IdRequest request) {
        return shopLabelOptionRelationService.listShopIdByLabelOptionId(request);
    }

    @Override
    @PostMapping("list_shopId_by_label_optionIds")
    public List<String> listShopIdByLabelOptionIds(@RequestBody @Validated IdSetRequest request) {
        return shopLabelOptionRelationService.listShopIdByLabelOptionIds(request);
    }

    @Override
    @PostMapping("count_shop_by_label_optionIds")
    public Map<String, Long> countShopByLabelOptionIds(@RequestBody @Validated IdSetRequest request) {
        return shopLabelOptionRelationService.countShopByLabelOptionIds(request);
    }

    @Override
    @PostMapping("map_label_optionId_by_shopIds")
    public Map<String, List<String>> mapLabelOptionIdByShopIds(@RequestBody @Validated IdSetRequest request) {
        return shopLabelOptionRelationService.mapLabelOptionIdByShopIds(request);
    }

}
