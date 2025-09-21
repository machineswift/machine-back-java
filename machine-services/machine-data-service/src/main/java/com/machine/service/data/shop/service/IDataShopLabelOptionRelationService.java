package com.machine.service.data.shop.service;

import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IDataShopLabelOptionRelationService {

    List<String> listLabelOptionIdByShopId(IdRequest request);

    List<String> listShopIdByLabelOptionId(IdRequest request);

    List<String> listShopIdByLabelOptionIds(IdSetRequest request);

    Map<String, Long> countShopByLabelOptionIds(IdSetRequest request);

    Map<String,List<String>> mapLabelOptionIdByShopIds(IdSetRequest request);

}
