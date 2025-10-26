package com.machine.service.data.tag.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.service.data.tag.dao.IDataTagDao;
import com.machine.service.data.tag.dao.mapper.DataTagMapper;
import com.machine.service.data.tag.dao.mapper.entity.DataTagEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataTagDaoImpl implements IDataTagDao {

    @Autowired
    private DataTagMapper tagMapper;

    @Override
    public String insert(DataTagEntity entity) {
        tagMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        DataTagEntity entity = tagMapper.selectById(id);
        if (entity == null) {
            return 0;
        }
        return tagMapper.deleteById(id);
    }

    @Override
    public int update(DataTagEntity entity) {
        return tagMapper.updateById(entity);
    }

    @Override
    public DataTagEntity getById(String id) {
        return tagMapper.selectById(id);
    }

    @Override
    public DataTagEntity getByCategoryIdAndName(String categoryId, String name) {
        Wrapper<DataTagEntity> queryWrapper = new LambdaQueryWrapper<DataTagEntity>()
                .eq(DataTagEntity::getCategoryId, categoryId)
                .eq(DataTagEntity::getName, name);
        return tagMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataTagEntity> selectByCategoryId(String categoryId) {
        Wrapper<DataTagEntity> queryWrapper = new LambdaQueryWrapper<DataTagEntity>()
                .eq(DataTagEntity::getCategoryId, categoryId);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public Page<DataTagEntity> selectPage(DataTagQueryPageInputDto inputDto) {
        IPage<DataTagEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return tagMapper.selectPage(inputDto, page);
    }
}
