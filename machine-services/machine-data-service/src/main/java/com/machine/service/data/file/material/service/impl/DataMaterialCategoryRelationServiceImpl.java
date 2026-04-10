package com.machine.service.data.file.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.file.material.dao.IDataMaterialCategoryRelationDao;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryRelationEntity;
import com.machine.service.data.file.material.service.IDataMaterialCategoryRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataMaterialCategoryRelationServiceImpl implements IDataMaterialCategoryRelationService {

    @Autowired
    private IDataMaterialCategoryRelationDao materialCategoryRelationDao;

    @Override
    public List<DataMaterialCategoryRelationOutputDto> listByMaterialId(IdRequest request) {
        List<DataMaterialCategoryRelationEntity> entityList = materialCategoryRelationDao.listByMaterialId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialCategoryRelationOutputDto.class);
    }

    @Override
    public List<DataMaterialCategoryRelationOutputDto> listByMaterialIdSet(IdSetRequest request) {
        List<DataMaterialCategoryRelationEntity> entityList = materialCategoryRelationDao.listByMaterialIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialCategoryRelationOutputDto.class);
    }
}
