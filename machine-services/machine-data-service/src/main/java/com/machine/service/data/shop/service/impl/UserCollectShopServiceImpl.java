package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.SuperShopCollectIdInputDto;
import com.machine.client.data.shop.dto.input.SuperShopListCollectShopInputDto;
import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.dao.IShopDao;
import com.machine.service.data.shop.dao.IUserCollectShopDao;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;
import com.machine.service.data.shop.service.IUserCollectShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Slf4j
@Service
public class UserCollectShopServiceImpl implements IUserCollectShopService {

    @Autowired
    private IShopDao shopDao;

    @Autowired
    private IUserCollectShopDao userCollectShopDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collectId(SuperShopCollectIdInputDto inputDto) {
        Set<String> unCollectShopIdSet = inputDto.getUnCollectShopIdSet();
        Set<String> collectShopIdSet = inputDto.getCollectShopIdSet();

        if (CollectionUtil.isNotEmpty(unCollectShopIdSet)) {
            userCollectShopDao.deleteByShopIdSet(AppContext.getContext().getUserId(), unCollectShopIdSet);
        }

        if (CollectionUtil.isNotEmpty(collectShopIdSet)) {
            //查询已经被收藏的门店
            List<String> shopIdList = userCollectShopDao.listCollectedIdByShopIdSet(AppContext.getContext().getUserId(), collectShopIdSet);
            if (CollectionUtil.isNotEmpty(shopIdList)) {
                shopIdList.forEach(collectShopIdSet::remove);
            }

            if (CollectionUtil.isNotEmpty(collectShopIdSet)) {
                userCollectShopDao.insertByShopIdSet(AppContext.getContext().getUserId(), collectShopIdSet);
            }
        }
    }

    @Override
    public List<String> listCollectedIdByShopIdSet(IdSetRequest request) {
        return userCollectShopDao.listCollectedIdByShopIdSet(AppContext.getContext().getUserId(), request.getIdSet());
    }

    @Override
    public Page<DataShopListOutputDto> pageCollectShop(SuperShopListCollectShopInputDto inputDto) {
        Page<ShopEntity> page = shopDao.pageCollectShop(inputDto);
        Page<DataShopListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataShopListOutputDto.class));
        return pageResult;
    }

}
