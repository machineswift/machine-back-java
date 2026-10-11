package com.machine.service.scm.attribute.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.service.scm.attribute.dao.IScmAttributeGroupDao;
import com.machine.service.scm.attribute.dao.mapper.ScmAttributeGroupMapper;
import com.machine.service.scm.attribute.dao.mapper.entity.ScmAttributeGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ScmAttributeGroupDaoImpl implements IScmAttributeGroupDao {

    @Autowired
    private ScmAttributeGroupMapper attributeGroupMapper;

    @Override
    public String insert(ScmAttributeGroupEntity entity) {
        attributeGroupMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        return attributeGroupMapper.deleteById(id);
    }

    @Override
    public int update(ScmAttributeGroupEntity entity) {
        return attributeGroupMapper.updateById(entity);
    }

    @Override
    public ScmAttributeGroupEntity getById(String id) {
        return attributeGroupMapper.selectById(id);
    }

    @Override
    public ScmAttributeGroupEntity getByCode(String code) {
        LambdaQueryWrapper<ScmAttributeGroupEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupEntity>()
                .eq(ScmAttributeGroupEntity::getCode, code);
        return attributeGroupMapper.selectOne(wrapper);
    }

    @Override
    public List<ScmAttributeGroupEntity> listByIdSet(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }
        LambdaQueryWrapper<ScmAttributeGroupEntity> wrapper = new LambdaQueryWrapper<ScmAttributeGroupEntity>()
                .in(ScmAttributeGroupEntity::getId, idSet);
        return attributeGroupMapper.selectList(wrapper);
    }

    @Override
    public Page<ScmAttributeGroupEntity> selectPage(ScmAttributeGroupQueryPageInputDto inputDto, Set<String> groupIdIn) {
        Page<ScmAttributeGroupEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        if (null != groupIdIn && groupIdIn.isEmpty()) {
            return page;
        }
        return attributeGroupMapper.selectPage(inputDto, groupIdIn, page);
    }
}