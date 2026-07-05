package com.machine.service.scm.property.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.service.scm.property.dao.IScmPropertyDao;
import com.machine.service.scm.property.dao.mapper.ScmPropertyMapper;
import com.machine.service.scm.property.dao.mapper.entity.ScmPropertyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScmPropertyDaoImpl implements IScmPropertyDao {

    @Autowired
    private ScmPropertyMapper propertyMapper;

    @Override
    public String insert(ScmPropertyEntity entity) {
        propertyMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return propertyMapper.deleteById(id);
    }

    @Override
    public int update(ScmPropertyEntity entity) {
        return propertyMapper.updateById(entity);
    }

    @Override
    public long countByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return 0L;
        }
        Wrapper<ScmPropertyEntity> wrapper = new LambdaQueryWrapper<ScmPropertyEntity>()
                .eq(ScmPropertyEntity::getCode, code);
        return propertyMapper.selectCount(wrapper);
    }

    @Override
    public long countByName(String name) {
        if (StrUtil.isBlank(name)) {
            return 0L;
        }
        Wrapper<ScmPropertyEntity> wrapper = new LambdaQueryWrapper<ScmPropertyEntity>()
                .eq(ScmPropertyEntity::getName, name);
        return propertyMapper.selectCount(wrapper);
    }

    @Override
    public long countByNameExcludeId(String name, String excludeId) {
        if (StrUtil.isBlank(name)) {
            return 0L;
        }
        LambdaQueryWrapper<ScmPropertyEntity> wrapper = new LambdaQueryWrapper<ScmPropertyEntity>()
                .eq(ScmPropertyEntity::getName, name);
        if (StrUtil.isNotBlank(excludeId)) {
            wrapper.ne(ScmPropertyEntity::getId, excludeId);
        }
        return propertyMapper.selectCount(wrapper);
    }

    @Override
    public ScmPropertyEntity getById(String id) {
        return propertyMapper.selectById(id);
    }

    @Override
    public List<ScmPropertyEntity> listAll() {
        Wrapper<ScmPropertyEntity> wrapper = new LambdaQueryWrapper<ScmPropertyEntity>()
                .orderByDesc(ScmPropertyEntity::getUpdateTime);
        return propertyMapper.selectList(wrapper);
    }

    @Override
    public Page<ScmPropertyEntity> selectPage(ScmPropertyQueryPageInputDto inputDto) {
        long current = inputDto.getCurrent() == null ? 1L : inputDto.getCurrent();
        long size = inputDto.getSize() == null ? 20L : inputDto.getSize();
        Page<ScmPropertyEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<ScmPropertyEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(inputDto.getCode())) {
            wrapper.like(ScmPropertyEntity::getCode, inputDto.getCode());
        }
        if (StrUtil.isNotBlank(inputDto.getName())) {
            wrapper.like(ScmPropertyEntity::getName, inputDto.getName());
        }
        if (inputDto.getPropertyType() != null) {
            wrapper.eq(ScmPropertyEntity::getPropertyType, inputDto.getPropertyType());
        }
        if (inputDto.getInputType() != null) {
            wrapper.eq(ScmPropertyEntity::getInputType, inputDto.getInputType());
        }
        if (inputDto.getIsSearch() != null) {
            wrapper.eq(ScmPropertyEntity::getIsSearch, inputDto.getIsSearch());
        }
        wrapper.orderByDesc(ScmPropertyEntity::getUpdateTime);
        return propertyMapper.selectPage(page, wrapper);
    }
}
