package com.machine.service.data.tag.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.DataTagOptionQueryListInputDto;
import com.machine.service.data.tag.dao.IDataTagOptionDao;
import com.machine.service.data.tag.dao.mapper.DataTagOptionMapper;
import com.machine.service.data.tag.dao.mapper.entity.DataTagOptionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataTagOptionDaoImpl implements IDataTagOptionDao {

    @Autowired
    private DataTagOptionMapper tagOptionMapper;

    @Override
    public String insert(DataTagOptionEntity entity) {
        tagOptionMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        return tagOptionMapper.deleteById(id);
    }

    @Override
    public int update(DataTagOptionEntity entity) {
        return tagOptionMapper.updateById(entity);
    }

    @Override
    public DataTagOptionEntity getById(String id) {
        return tagOptionMapper.selectById(id);
    }

    @Override
    public DataTagOptionEntity getByTagIdAndCode(String tagId, String code) {
        Wrapper<DataTagOptionEntity> queryWrapper = new LambdaQueryWrapper<DataTagOptionEntity>()
                .eq(DataTagOptionEntity::getTagId, tagId)
                .eq(DataTagOptionEntity::getCode, code);
        return tagOptionMapper.selectOne(queryWrapper);
    }

    @Override
    public DataTagOptionEntity getByTagIdAndName(String tagId, String name) {
        Wrapper<DataTagOptionEntity> queryWrapper = new LambdaQueryWrapper<DataTagOptionEntity>()
                .eq(DataTagOptionEntity::getTagId, tagId)
                .eq(DataTagOptionEntity::getName, name);
        return tagOptionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataTagOptionEntity> selectByTagId(String tagId) {
        Wrapper<DataTagOptionEntity> queryWrapper = new LambdaQueryWrapper<DataTagOptionEntity>()
                .eq(DataTagOptionEntity::getTagId, tagId)
                .orderByDesc(DataTagOptionEntity::getSort);
        return tagOptionMapper.selectList(queryWrapper);
    }
}
