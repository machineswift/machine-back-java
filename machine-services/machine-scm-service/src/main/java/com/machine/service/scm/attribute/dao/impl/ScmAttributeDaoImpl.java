package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.service.scm.attribute.dao.IScmAttributeDao;
import com.machine.service.scm.attribute.dao.mapper.ScmAttributeMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmAttributeDaoImpl implements IScmAttributeDao {

    @Autowired
    private ScmAttributeMapper attributeMapper;

    @Override
    public String insert(ScmAttributeEntity entity) {
        attributeMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return attributeMapper.deleteById(id);
    }

    @Override
    public int update(ScmAttributeEntity entity) {
        return attributeMapper.updateById(entity);
    }

    @Override
    public long countByName(String name) {
        if (StrUtil.isBlank(name)) {
            return 0L;
        }
        Wrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<ScmAttributeEntity>()
                .eq(ScmAttributeEntity::getName, name);
        return attributeMapper.selectCount(wrapper);
    }

    @Override
    public long countByNameExcludeId(String name, String excludeId) {
        if (StrUtil.isBlank(name)) {
            return 0L;
        }
        LambdaQueryWrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<ScmAttributeEntity>()
                .eq(ScmAttributeEntity::getName, name);
        if (StrUtil.isNotBlank(excludeId)) {
            wrapper.ne(ScmAttributeEntity::getId, excludeId);
        }
        return attributeMapper.selectCount(wrapper);
    }

    @Override
    public ScmAttributeEntity getByCode(String code) {
        LambdaQueryWrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<ScmAttributeEntity>()
                .eq(ScmAttributeEntity::getCode, code);
        return attributeMapper.selectOne(wrapper);
    }

    @Override
    public ScmAttributeEntity getById(String id) {
        return attributeMapper.selectById(id);
    }

    @Override
    public List<ScmAttributeEntity> listByIdSet(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }
        Wrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<ScmAttributeEntity>()
                .in(ScmAttributeEntity::getId, idSet);
        return attributeMapper.selectList(wrapper);
    }

    @Override
    public List<ScmAttributeEntity> listAll() {
        Wrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<ScmAttributeEntity>()
                .orderByDesc(ScmAttributeEntity::getUpdateTime);
        return attributeMapper.selectList(wrapper);
    }

    @Override
    public Page<ScmAttributeEntity> selectPage(ScmAttributeQueryPageInputDto inputDto) {
        long current = inputDto.getCurrent() == null ? 1L : inputDto.getCurrent();
        long size = inputDto.getSize() == null ? 20L : inputDto.getSize();
        Page<ScmAttributeEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<ScmAttributeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(inputDto.getCode())) {
            wrapper.like(ScmAttributeEntity::getCode, inputDto.getCode());
        }
        if (StrUtil.isNotBlank(inputDto.getName())) {
            wrapper.like(ScmAttributeEntity::getName, inputDto.getName());
        }
        if (inputDto.getAttributeType() != null) {
            wrapper.eq(ScmAttributeEntity::getAttributeType, inputDto.getAttributeType());
        }
        if (inputDto.getInputType() != null) {
            wrapper.eq(ScmAttributeEntity::getInputType, inputDto.getInputType());
        }
        if (inputDto.getSearchableFlag() != null) {
            wrapper.eq(ScmAttributeEntity::getSearchableFlag, inputDto.getSearchableFlag());
        }
        wrapper.orderByDesc(ScmAttributeEntity::getUpdateTime);
        return attributeMapper.selectPage(page, wrapper);
    }
}
