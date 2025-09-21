package com.machine.service.data.attachment.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.attachment.dao.IDataAttachmentCategoryRelationDao;
import com.machine.service.data.attachment.dao.mapper.DataAttachmentCategoryRelationMapper;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataAttachmentCategoryRelationDaoImpl implements IDataAttachmentCategoryRelationDao {

    @Autowired
    private DataAttachmentCategoryRelationMapper attachmentCategoryRelationMapper;

    @Override
    public Long selectCountByCategoryId(String categoryId) {
        if (StrUtil.isBlank(categoryId)) {
            return 0L;
        }
        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .eq(DataAttachmentCategoryRelationEntity::getCategoryId, categoryId);
        return attachmentCategoryRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public Long selectCountByCategoryIdSet(Set<String> categoryIdSet) {
        if (CollectionUtil.isEmpty(categoryIdSet)) {
            return 0L;
        }
        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .in(DataAttachmentCategoryRelationEntity::getCategoryId, categoryIdSet);
        return attachmentCategoryRelationMapper.selectCount(queryWrapper);
    }

    @Override
    public void insert(DataAttachmentCategoryRelationEntity entity) {
        attachmentCategoryRelationMapper.insert(entity);
    }

    @Override
    public void deleteByAttachmentId(String attachmentId) {
        if (StrUtil.isBlank(attachmentId)) {
            return;
        }
        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .eq(DataAttachmentCategoryRelationEntity::getAttachmentId, attachmentId);
        attachmentCategoryRelationMapper.delete(queryWrapper);
    }

    @Override
    public List<DataAttachmentCategoryRelationEntity> listByAttachmentId(String attachmentId) {
        if (StrUtil.isBlank(attachmentId)) {
            return List.of();
        }
        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .eq(DataAttachmentCategoryRelationEntity::getCategoryId, attachmentId);
        return attachmentCategoryRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataAttachmentCategoryRelationEntity> listByCategoryIdSet(Set<String> categoryIdSet) {
        if (CollectionUtil.isEmpty(categoryIdSet)) {
            return List.of();
        }

        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .in(DataAttachmentCategoryRelationEntity::getCategoryId, categoryIdSet);
        return attachmentCategoryRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataAttachmentCategoryRelationEntity> listByAttachmentIdSet(Set<String> attachmentIdSet) {
        if (CollectionUtil.isEmpty(attachmentIdSet)) {
            return List.of();
        }

        Wrapper<DataAttachmentCategoryRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryRelationEntity>()
                .in(DataAttachmentCategoryRelationEntity::getAttachmentId, attachmentIdSet);
        return attachmentCategoryRelationMapper.selectList(queryWrapper);
    }
}
