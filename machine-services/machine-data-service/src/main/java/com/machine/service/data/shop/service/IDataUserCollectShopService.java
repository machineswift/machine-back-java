package com.machine.service.data.shop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.DataSuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.DataSuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IDataUserCollectShopService {

    void collectId(DataSuperShopCollectIdInputDto inputDto);

    List<String> listCollectedIdByShopIdSet(IdSetRequest request);

    Page<DataShopListOutputDto> pageCollectShop(DataSuperShopListCollectShopInputDto inputDto);

}
