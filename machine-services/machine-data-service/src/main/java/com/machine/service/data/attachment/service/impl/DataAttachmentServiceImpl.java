package com.machine.service.data.attachment.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.dto.input.DataAttachmentBindTableNameInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentUpdateInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentListOutputDto;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.attachment.dao.IDataAttachmentCategoryRelationDao;
import com.machine.service.data.attachment.dao.IDataAttachmentDao;
import com.machine.service.data.attachment.dao.IDataAttachmentTableRelationDao;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryRelationEntity;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentEntity;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentTableRelationEntity;
import com.machine.service.data.attachment.service.IDataAttachmentService;
import com.machine.starter.redis.cache.data.RedisCacheDataAttachmentCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonDataConstant.AttachmentCategory.DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE;

@Slf4j
@Service
public class DataAttachmentServiceImpl implements IDataAttachmentService {

    /**
     * 30年
     */
    private static final long PERMANENT_EXPIRY_MILLISECONDS = 30 * 365 * 24 * 60 * 60 * 1000L;

    /**
     * 30天
     */
    private static final long TEMPORARY_EXPIRY_MILLISECONDS = 30 * 24 * 60 * 60 * 1000L;

    @Autowired
    private RedisCacheDataAttachmentCategory attachmentCategoryCache;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Autowired
    private IDataAttachmentCategoryRelationDao attachmentCategoryRelationDao;

    @Autowired
    private IDataAttachmentTableRelationDao attachmentTableRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTemporary(DataAttachmentCreateTemporaryInputDto inputDto) {
        DataAttachmentEntity entity = new DataAttachmentEntity();
        entity.setStatus(DataAttachmentStatusEnum.PUBLISHED);
        entity.setType(inputDto.getType());
        entity.setStorageType(SystemStorageTypeEnum.TEMPORARY);
        entity.setTitle(inputDto.getName());
        entity.setName(inputDto.getName());
        entity.setFileInfo(inputDto.getFileInfo());
        entity.setSize(inputDto.getSize());
        entity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        return attachmentDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataAttachmentUpdateInputDto inputDto) {
        String attachmentId = inputDto.getId();
        DataAttachmentEntity dbEntity = attachmentDao.getById(attachmentId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.attachment.service.update.attachmentNotExists", "附件不存在");
        }

        DataAttachmentEntity updateEntity = new DataAttachmentEntity();
        updateEntity.setId(attachmentId);
        updateEntity.setStatus(DataAttachmentStatusEnum.PUBLISHED);
        updateEntity.setTitle(inputDto.getTitle());
        if (null == inputDto.getExpireTime()) {
            updateEntity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        } else {
            inputDto.setExpireTime(System.currentTimeMillis() + PERMANENT_EXPIRY_MILLISECONDS);
        }
        attachmentDao.update(updateEntity);

        //素材与分类的关系
        attachmentCategoryRelationDao.deleteByAttachmentId(attachmentId);
        insertAttachmentCategoryRelation(attachmentId, inputDto.getCategoryIdSet());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindTableName(DataAttachmentBindTableNameInputDto inputDto) {
        DataAttachmentTableRelationEntity dbEntity = attachmentTableRelationDao.selectByUk(
                inputDto.getId(), inputDto.getTableName(), inputDto.getDataId());
        if (null != dbEntity) {
            return;
        }

        DataAttachmentTableRelationEntity insertEntity = new DataAttachmentTableRelationEntity();
        insertEntity.setAttachmentId(inputDto.getId());
        insertEntity.setTableName(inputDto.getTableName());
        insertEntity.setDataId(inputDto.getDataId());
        attachmentTableRelationDao.insert(insertEntity);
    }

    @Override
    public DataAttachmentDetailOutputDto getById(IdRequest request) {
        DataAttachmentEntity dbEntity = attachmentDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataAttachmentDetailOutputDto.class);
    }

    @Override
    public List<DataAttachmentDetailOutputDto> listByIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataAttachmentEntity> entityList = attachmentDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataAttachmentDetailOutputDto.class);
    }

    @Override
    public Page<DataAttachmentListOutputDto> selectPage(DataAttachmentQueryPageInputDto inputDto) {
        Set<String> categoryIdSet = inputDto.getCategoryIdSet();

        // 处理素材分类参数
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE)) {
                inputDto.setContainVirtualNode(true);
            }
            Set<String> recursionCategoryIdSet = attachmentCategoryCache.recursionListSubId(categoryIdSet);
            inputDto.setCategoryIdSet(recursionCategoryIdSet);
        }

        Page<DataAttachmentEntity> entityPage = attachmentDao.selectPage(inputDto);
        Page<DataAttachmentListOutputDto> pageResult = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());

        if (CollectionUtil.isEmpty(entityPage.getRecords())) {
            return pageResult;
        }

        List<DataAttachmentListOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityPage.getRecords()), DataAttachmentListOutputDto.class);
        pageResult.setRecords(outputDtoList);
        return pageResult;
    }


    private void insertAttachmentCategoryRelation(String attachmentId,
                                                  Set<String> categoryIdSet) {
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_ATTACHMENT_CATEGORY_VIRTUAL_NODE)) {
                throw new DataBusinessException("data.attachment.service.createPermanent.virtualNode", "不能选择【未分类】");
            }

            for (String categoryId : categoryIdSet) {
                DataAttachmentCategoryRelationEntity relationEntity = new DataAttachmentCategoryRelationEntity();
                relationEntity.setCategoryId(categoryId);
                relationEntity.setAttachmentId(attachmentId);
                attachmentCategoryRelationDao.insert(relationEntity);
            }
        }
    }
}
