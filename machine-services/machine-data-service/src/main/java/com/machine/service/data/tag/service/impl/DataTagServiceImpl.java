package com.machine.service.data.tag.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagListOutputDto;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.tag.dao.IDataTagDao;
import com.machine.service.data.tag.dao.IDataTagCategoryDao;
import com.machine.service.data.tag.dao.IDataTagOptionDao;
import com.machine.service.data.tag.dao.mapper.entity.DataTagEntity;
import com.machine.service.data.tag.dao.mapper.entity.DataTagCategoryEntity;
import com.machine.service.data.tag.service.IDataTagService;
import com.machine.starter.redis.cache.data.RedisCacheDataTagCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
public class DataTagServiceImpl implements IDataTagService {

    @Autowired
    private RedisCacheDataTagCategory dataTagCategoryCache;

    @Autowired
    private IDataTagDao tagDao;

    @Autowired
    private IDataTagCategoryDao tagCategoryDao;

    @Autowired
    private IDataTagOptionDao dataTagOptionDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataTagCreateInputDto inputDto) {
        // 验证分类ID是否存在
        DataTagCategoryEntity categoryEntity = tagCategoryDao.getById(inputDto.getCategoryId());
        if (categoryEntity == null) {
            throw new DataBusinessException("data.tag.service.create.categoryIdNotExists", "分类ID不存在");
        }

        // 验证编码是否已存在
        DataTagEntity entityByCode = tagDao.getByCode(inputDto.getCode());
        if (entityByCode != null) {
            throw new DataBusinessException("data.tag.service.create.codeAlreadyExists", "标签编码已存在");
        }

        // 验证名称是否已存在
        DataTagEntity existingTag = tagDao.getByCategoryIdAndName(inputDto.getCategoryId(), inputDto.getName());
        if (existingTag != null) {
            throw new DataBusinessException("data.tag.service.create.nameAlreadyExists", "标签名称已存在");
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setCategoryId(inputDto.getCategoryId());
        entity.setCode(inputDto.getCode());
        entity.setName(inputDto.getName());
        entity.setSort(inputDto.getSort());
        entity.setDescription(inputDto.getDescription());
        return tagDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataTagCategoryEntity entity = tagCategoryDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        //判断智能标签分类是否关联标签选项
        if (CollectionUtil.isNotEmpty(dataTagOptionDao.selectByTagId(entity.getId()))) {
            throw new DataBusinessException("data.tagCategory.service.delete.associationTagOption", "关联标签选项不能删除");
        }

        return tagDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataTagUpdateInputDto inputDto) {
        DataTagEntity dbEntity = tagDao.getById(inputDto.getId());
        if (dbEntity == null) {
            throw new DataBusinessException("data.tag.service.update.idNotExists", "标签ID不存在");
        }

        // 验证名称是否已存在（排除当前记录）
        DataTagEntity existingTag = tagDao.getByCategoryIdAndName(dbEntity.getCategoryId(), inputDto.getName());
        if (existingTag != null && !existingTag.getId().equals(inputDto.getId())) {
            throw new DataBusinessException("data.tag.service.update.nameAlreadyExists", "标签名称已存在");
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setId(inputDto.getId());
        entity.setName(inputDto.getName());
        entity.setDescription(inputDto.getDescription());
        return tagDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCode(DataTagUpdateCodeInputDto inputDto) {
        DataTagEntity dbEntity = tagDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getCode().equals(inputDto.getCode())) {
            //相同直接返回
            return 0;
        }

        // 验证编码是否已存在
        DataTagEntity entityByCode = tagDao.getByCode(inputDto.getCode());
        if (entityByCode != null && !entityByCode.getId().equals(inputDto.getId())) {
            throw new DataBusinessException("data.tag.service.updateCode.codeAlreadyExists", "标签编码已存在");
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setId(inputDto.getId());
        entity.setCode(inputDto.getCode());
        return tagDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataTagUpdateStatusInputDto inputDto) {
        DataTagEntity dbEntity = tagDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getStatus().equals(inputDto.getStatus())) {
            //相同直接返回
            return 0;
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setId(inputDto.getId());
        entity.setStatus(inputDto.getStatus());
        return tagDao.update(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSort(DataTagUpdateSortInputDto inputDto) {
        DataTagEntity dbEntity = tagDao.getById(inputDto.getId());
        if (dbEntity == null) {
            return 0;
        }

        if (dbEntity.getSort().equals(inputDto.getSort())) {
            //相同直接返回
            return 0;
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setId(inputDto.getId());
        entity.setSort(inputDto.getSort());
        return tagDao.update(entity);
    }

    @Override
    public int updateCategory(DataTagUpdateCategoryDto inputDto) {
        DataTagEntity dbEntity = tagDao.getById(inputDto.getId());
        if (dbEntity == null) {
            throw new DataBusinessException("data.tag.service.updateCategory.idNotExists", "标签ID不存在");
        }

        // 验证分类ID是否存在
        DataTagCategoryEntity categoryEntity = tagCategoryDao.getById(inputDto.getCategoryId());
        if (categoryEntity == null) {
            throw new DataBusinessException("data.tag.service.updateCategory.categoryIdNotExists", "分类ID不存在");
        }

        // 验证名称是否已存在（排除当前记录）
        DataTagEntity existingTag = tagDao.getByCategoryIdAndName(inputDto.getCategoryId(), dbEntity.getName());
        if (existingTag != null && !existingTag.getId().equals(inputDto.getId())) {
            throw new DataBusinessException("data.tag.service.updateCategory.nameAlreadyExists", "标签名称已存在");
        }

        DataTagEntity entity = new DataTagEntity();
        entity.setId(inputDto.getId());
        entity.setCategoryId(inputDto.getCategoryId());
        return tagDao.update(entity);
    }

    @Override
    public DataTagDetailOutputDto detail(IdRequest request) {
        DataTagEntity entity = tagDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataTagDetailOutputDto.class);
    }

    @Override
    public Page<DataTagListOutputDto> selectPage(DataTagQueryPageInputDto inputDto) {
        if (CollectionUtil.isNotEmpty(inputDto.getCategoryIdSet())) {
            // 获取分类以及子分类
            Set<String> categoryIdSet = dataTagCategoryCache.recursionListSubIds(inputDto.getType(), inputDto.getCategoryIdSet());
            inputDto.setCategoryIdSet(categoryIdSet);
        }

        Page<DataTagEntity> page = tagDao.selectPage(inputDto);
        Page<DataTagListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataTagListOutputDto.class));
        return pageResult;
    }

}
