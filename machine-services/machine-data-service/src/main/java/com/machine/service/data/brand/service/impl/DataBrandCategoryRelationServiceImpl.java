package com.machine.service.data.brand.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.brand.dto.output.DataBrandCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.brand.dao.IDataBrandCategoryRelationDao;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryRelationEntity;
import com.machine.service.data.brand.service.IDataBrandCategoryRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataBrandCategoryRelationServiceImpl implements IDataBrandCategoryRelationService {

    @Autowired
    private IDataBrandCategoryRelationDao brandCategoryRelationDao;

    @Override
    public List<DataBrandCategoryRelationOutputDto> listByBrandId(IdRequest request) {
        List<DataBrandCategoryRelationEntity> entityList = brandCategoryRelationDao.listByBrandId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataBrandCategoryRelationOutputDto.class);
    }

    @Override
    public List<DataBrandCategoryRelationOutputDto> listByBrandIdSet(IdSetRequest request) {
        List<DataBrandCategoryRelationEntity> entityList = brandCategoryRelationDao.listByBrandIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataBrandCategoryRelationOutputDto.class);
    }
}
