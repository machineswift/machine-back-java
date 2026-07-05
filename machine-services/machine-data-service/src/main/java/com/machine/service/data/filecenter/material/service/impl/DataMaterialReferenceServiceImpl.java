package com.machine.service.data.filecenter.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialReferenceDetailOutputDto;
import com.machine.service.data.filecenter.material.dao.IDataMaterialReferenceDao;
import com.machine.service.data.filecenter.material.dao.mapper.entity.DataMaterialReferenceEntity;
import com.machine.service.data.filecenter.material.service.IDataMaterialReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataMaterialReferenceServiceImpl implements IDataMaterialReferenceService {

    @Autowired
    private IDataMaterialReferenceDao materialReferenceDao;

    @Override
    public List<DataMaterialReferenceDetailOutputDto> listByMaterialId(String materialId) {
        List<DataMaterialReferenceEntity> entityList = materialReferenceDao.listByMaterialId(materialId);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialReferenceDetailOutputDto.class);
    }

    @Override
    public Map<String, List<DataMaterialReferenceDetailOutputDto>> mapByMaterialIdSet(Set<String> materialIdSet) {
        if (CollectionUtil.isEmpty(materialIdSet)) {
            return new HashMap<>();
        }

        List<DataMaterialReferenceEntity> entityList = materialReferenceDao.listByMaterialIdSet(materialIdSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return new HashMap<>();
        }

        List<DataMaterialReferenceDetailOutputDto> outputDtoList =
                JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialReferenceDetailOutputDto.class);

        return outputDtoList.stream()
                .collect(Collectors.groupingBy(DataMaterialReferenceDetailOutputDto::getMaterialId));
    }

    @Override
    public List<DataMaterialReferenceDetailOutputDto> listByEntity(String entity, String entityId) {
        List<DataMaterialReferenceEntity> entityList = materialReferenceDao.listByEntity(entity, entityId);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialReferenceDetailOutputDto.class);
    }
}
