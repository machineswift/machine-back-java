package com.machine.service.data.file.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.material.dto.input.DataMaterialCreatePermanentInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.file.material.dao.*;
import com.machine.service.data.file.material.dao.mapper.entity.*;
import com.machine.service.data.file.material.service.IDataMaterialService;
import com.machine.starter.redis.cache.data.RedisCacheDataMaterialCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;

@Slf4j
@Service
public class DataMaterialServiceImpl implements IDataMaterialService {

    /**
     * 30年
     */
    private static final long PERMANENT_EXPIRY_MILLISECONDS = 30 * 365 * 24 * 60 * 60 * 1000L;

    /**
     * 30天
     */
    private static final long TEMPORARY_EXPIRY_MILLISECONDS = 30 * 24 * 60 * 60 * 1000L;

    @Autowired
    private RedisCacheDataMaterialCategory materialCategoryCache;

    @Autowired
    private IDataMaterialDao materialDao;

    @Autowired
    private IDataMaterialCategoryRelationDao materialCategoryRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPermanent(DataMaterialCreatePermanentInputDto inputDto) {
        String materialId = inputDto.getMaterialId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.createPermanent.materialNotExists", "素材不存在");
        }


        DataMaterialEntity updateEntity = new DataMaterialEntity();
        updateEntity.setId(materialId);
        updateEntity.setTitle(inputDto.getTitle());
        if (null == inputDto.getExpireTime()) {
            updateEntity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        } else {
            inputDto.setExpireTime(System.currentTimeMillis() + PERMANENT_EXPIRY_MILLISECONDS);
        }
        materialDao.update(updateEntity);

        //素材与分类的关系
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermanent(DataMaterialUpdateInputDto inputDto) {
        String materialId = inputDto.getId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.updatePermanent.materialNotExists", "素材不存在");
        }

        DataMaterialEntity updateEntity = new DataMaterialEntity();
        updateEntity.setId(materialId);
        updateEntity.setTitle(inputDto.getTitle());
        if (null == inputDto.getExpireTime()) {
            updateEntity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        } else {
            inputDto.setExpireTime(System.currentTimeMillis() + PERMANENT_EXPIRY_MILLISECONDS);
        }
        materialDao.update(updateEntity);


        //素材与分类的关系
        materialCategoryRelationDao.deleteByMaterialId(materialId);
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }

    @Override
    public DataMaterialDetailOutputDto getById(IdRequest request) {
        DataMaterialEntity dbEntity = materialDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataMaterialDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataMaterialDetailOutputDto.class);
        return outputDto;
    }

    @Override
    public List<DataMaterialDetailOutputDto> listByIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataMaterialEntity> entityList = materialDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<DataMaterialDetailOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialDetailOutputDto.class);
        return outputDtoList;
    }

    @Override
    public Page<DataMaterialListOutputDto> selectPage(DataMaterialQueryPageInputDto inputDto) {
        Set<String> categoryIdSet = inputDto.getCategoryIdSet();

        // 处理素材分类参数
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE)) {
                inputDto.setContainVirtualNode(true);
            }
            Set<String> recursionCategoryIdSet = materialCategoryCache.recursionListSubId(categoryIdSet);
            inputDto.setCategoryIdSet(recursionCategoryIdSet);
        }

        Page<DataMaterialEntity> entityPage = materialDao.selectPage(inputDto);
        Page<DataMaterialListOutputDto> pageResult = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());

        if (CollectionUtil.isEmpty(entityPage.getRecords())) {
            return pageResult;
        }

        List<DataMaterialListOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityPage.getRecords()), DataMaterialListOutputDto.class);
        pageResult.setRecords(outputDtoList);
        return pageResult;
    }

    private void insertMaterialCategoryRelation(String materialId,
                                                Set<String> categoryIdSet) {
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE)) {
                throw new DataBusinessException("data.material.service.createPermanent.virtualNode", "不能选择【未分类】");
            }

            for (String categoryId : categoryIdSet) {
                DataMaterialCategoryRelationEntity relationEntity = new DataMaterialCategoryRelationEntity();
                relationEntity.setCategoryId(categoryId);
                relationEntity.setMaterialId(materialId);
                materialCategoryRelationDao.insert(relationEntity);
            }
        }
    }


}
