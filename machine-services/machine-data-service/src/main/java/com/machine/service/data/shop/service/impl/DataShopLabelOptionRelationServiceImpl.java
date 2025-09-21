package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.dao.IDataShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.DataShopLabelOptionRelationEntity;
import com.machine.service.data.shop.service.IDataShopLabelOptionRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataShopLabelOptionRelationServiceImpl implements IDataShopLabelOptionRelationService {

    @Autowired
    private IDataShopLabelOptionRelationDao shopLabelOptionRelationDao;

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
        List<DataShopLabelOptionRelationEntity> relationEntityList = shopLabelOptionRelationDao.listByShopIds(request.getIdSet());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return Map.of();
        }
        return relationEntityList.stream().collect(Collectors.groupingBy(DataShopLabelOptionRelationEntity::getShopId,
                Collectors.mapping(DataShopLabelOptionRelationEntity::getLabelOptionId, Collectors.toList())));
    }
}
