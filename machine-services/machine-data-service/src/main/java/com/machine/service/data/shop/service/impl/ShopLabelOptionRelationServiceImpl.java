package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.machine.client.data.shop.dto.output.ShopOrganizationRelationListOutputDto;
import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.dao.IShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.ShopLabelOptionRelationEntity;
import com.machine.service.data.shop.service.IShopLabelOptionRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShopLabelOptionRelationServiceImpl implements IShopLabelOptionRelationService {

    @Autowired
    private IShopLabelOptionRelationDao shopLabelOptionRelationDao;

    @Override
    public List<String> listLabelOptionIdByShopId(IdRequest request) {
        return shopLabelOptionRelationDao.listLabelOptionIdByShopId(request.getId());
    }

    @Override
    public List<String> listShopIdByLabelOptionId(IdRequest request) {
        return shopLabelOptionRelationDao.listShopIdByLabelOptionId(request.getId());
    }


    @Override
    public List<String> listShopIdByLabelOptionIds(IdSetRequest request) {
        return shopLabelOptionRelationDao.listShopIdByLabelOptionIds(request.getIdSet());
    }

    @Override
    public Map<String, Long> countShopByLabelOptionIds(IdSetRequest request) {
        List<IdCountDto> countDtoList = shopLabelOptionRelationDao.countShopByLabelOptionIds(request.getIdSet());
        if (CollectionUtil.isEmpty(countDtoList)) {
            return Map.of();
        }
        return countDtoList.stream()
                .collect(Collectors.toMap(IdCountDto::getId, IdCountDto::getCount));
    }

    @Override
    public Map<String, List<String>> mapLabelOptionIdByShopIds(IdSetRequest request) {
        List<ShopLabelOptionRelationEntity> relationEntityList = shopLabelOptionRelationDao.listByShopIds(request.getIdSet());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return Map.of();
        }
        return relationEntityList.stream().collect(Collectors.groupingBy(ShopLabelOptionRelationEntity::getShopId,
                Collectors.mapping(ShopLabelOptionRelationEntity::getLabelOptionId, Collectors.toList())));
    }
}
