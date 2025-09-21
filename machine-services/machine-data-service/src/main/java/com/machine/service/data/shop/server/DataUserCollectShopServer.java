package com.machine.service.data.shop.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.IDataUserCollectShopClient;
import com.machine.client.data.shop.dto.input.DataSuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.DataSuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.shop.service.IDataUserCollectShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/user_collect_shop")
public class DataUserCollectShopServer implements IDataUserCollectShopClient {

    @Autowired
    private IDataUserCollectShopService userCollectShopService;

    @Override
    @PostMapping("collect_shop")
    public void collectShop(@RequestBody @Validated DataSuperShopCollectIdInputDto inputDto) {
        log.info("收藏门店，inputDto={}", JSONUtil.toJsonStr(inputDto));
        userCollectShopService.collectId(inputDto);
    }

    @Override
    @PostMapping("list_collectedId_by_shopIdSet")
    public List<String> listCollectedIdByShopIdSet(@RequestBody @Validated IdSetRequest request) {
        return userCollectShopService.listCollectedIdByShopIdSet(request);
    }

    @Override
    @PostMapping("page_collect_shop")
    public PageResponse<DataShopListOutputDto> pageCollectShop(@RequestBody @Validated DataSuperShopListCollectShopInputDto inputDto) {
        Page<DataShopListOutputDto> pageResult = userCollectShopService.pageCollectShop(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
