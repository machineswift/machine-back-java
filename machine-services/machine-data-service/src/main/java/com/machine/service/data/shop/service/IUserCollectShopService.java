package com.machine.service.data.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.SuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.SuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IUserCollectShopService {

    void collectId(SuperShopCollectIdInputDto inputDto);

    List<String> listCollectedIdByShopIdSet(IdSetRequest request);

    Page<DataShopListOutputDto> pageCollectShop(SuperShopListCollectShopInputDto inputDto);

}
