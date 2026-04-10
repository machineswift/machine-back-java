package com.machine.service.data.file.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.material.dto.input.DataMaterialCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateCategoryInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.base.envm.data.file.material.DataMaterialAuditStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialBusinessStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialProcessStatusEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.file.attachment.dao.IDataAttachmentDao;
import com.machine.service.data.file.attachment.dao.IDataFileDao;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataAttachmentEntity;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataFileEntity;
import com.machine.service.data.file.material.dao.*;
import com.machine.service.data.file.material.dao.mapper.entity.*;
import com.machine.service.data.file.material.service.IDataMaterialService;
import com.machine.starter.redis.cache.data.RedisCacheDataMaterialCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.machine.sdk.base.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;

@Slf4j
@Service
public class DataMaterialServiceImpl implements IDataMaterialService {

    @Autowired
    private RedisCacheDataMaterialCategory materialCategoryCache;

    @Autowired
    private IDataFileDao fileDao;

    @Autowired
    private IDataMaterialDao materialDao;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Autowired
    private IDataMaterialCategoryRelationDao materialCategoryRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataMaterialCreateInputDto inputDto) {
        DataMaterialEntity entity = new DataMaterialEntity();
        entity.setFileType(inputDto.getFileType());
        entity.setTitle(inputDto.getTitle());
        entity.setProcessStatus(DataMaterialProcessStatusEnum.NOT_STARTED);
        entity.setBusinessStatus(DataMaterialBusinessStatusEnum.DRAFT);
        entity.setAuditStatus(DataMaterialAuditStatusEnum.NOT_SUBMITTED);
        String materialId = materialDao.insert(entity);

        //素材与分类的关系
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
        return materialId;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataMaterialUpdateInputDto inputDto) {
        String materialId = inputDto.getId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.updatePermanent.materialNotExists", "素材不存在");
        }

        String attachmentId = inputDto.getAttachmentId();
        DataAttachmentEntity attachmentEntity = attachmentDao.getById(attachmentId);
        if (attachmentEntity == null) {
            throw new DataBusinessException("data.material.service.updatePermanent.attachmentNotExists", "附件不存在");
        }

        String fileId = attachmentEntity.getFileId();
        DataFileEntity fileEntity = fileDao.getById(fileId);
        if (dbEntity.getFileType() != fileEntity.getFileType()) {
            throw new DataBusinessException("data.material.service.updatePermanent.wrongFileType", "附件类型错误");
        }

        DataMaterialEntity updateEntity = new DataMaterialEntity();
        updateEntity.setId(materialId);
        updateEntity.setAttachmentId(inputDto.getAttachmentId());
        updateEntity.setTitle(inputDto.getTitle());
        materialDao.update(updateEntity);

        //素材与分类的关系
        materialCategoryRelationDao.deleteByMaterialId(materialId);
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(DataMaterialUpdateCategoryInputDto inputDto) {
        String materialId = inputDto.getId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.updateCategory.materialNotExists", "素材不存在");
        }
        materialCategoryRelationDao.deleteByMaterialId(materialId);
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }

    @Override
    public DataMaterialDetailOutputDto getById(IdRequest request) {
        DataMaterialEntity dbEntity = materialDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataMaterialDetailOutputDto.class);
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

        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialDetailOutputDto.class);
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
